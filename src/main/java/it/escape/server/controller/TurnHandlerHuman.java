package it.escape.server.controller;


import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.cardactions.DrawObjectCard;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.cards.objectCards.AdrenalineCard;
import it.escape.server.model.game.cards.objectCards.AttackCard;
import it.escape.server.model.game.cards.objectCards.LightsCard;
import it.escape.server.model.game.cards.objectCards.SedativesCard;
import it.escape.server.model.game.cards.objectCards.TeleportCard;
import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.server.model.game.exceptions.WrongCardException;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

public class TurnHandlerHuman extends TurnHandler {
	
	private UserMessagesReporter reporter;
	private ObjectCardAction objectCardAction;
	private ObjectCard objectCard;
	private boolean correctInput;
	
	public TurnHandlerHuman(Player currentPlayer, MapActionInterface map) {
		super(map);
		this.currentPlayer=(Human)currentPlayer;
	}
	
	private void playObjectCard() {
		do {
			try {
				boolean restrictions = true;
				String key = reporter.askWhichObjectCard();
				objectCard = currentPlayer.drawCard(key);  // this card is removed from the player's hand
														   			
					
					if (currentPlayer.HasMoved()) {		//after the move
						if (objectCard instanceof TeleportCard || objectCard instanceof LightsCard)
							restrictions = false;
					}
					else {	//before the move
						if ( objectCard instanceof SedativesCard || objectCard instanceof AdrenalineCard 
							|| objectCard instanceof TeleportCard || objectCard instanceof LightsCard)
							restrictions = false;
					}
					
					if (!restrictions) {
						objectCardAction = objectCard.getObjectAction();
						Announcer.getAnnouncerInstance().announceObjectCard(currentPlayer, objectCard); 
						objectCardAction.execute((Human)currentPlayer, map);
						endObjectCard = true;
					}
					else {
						throw new WrongCardException();
					}
				}
			catch (WrongCardException e) {	
				if (reporter.askIfObjectCard("Do you want to play an object card?"))
					endObjectCard = false;
				else 
					endObjectCard = true;
			}
			catch (CardNotPresentException e) {	
				if (reporter.askIfObjectCard("Do you want to play an object card?"))
					endObjectCard = false;
				else
					endObjectCard = true;
			}
		} while (!endObjectCard);
	}
	
	private void discardObjectCard() {
		do {
			try {
				String key = reporter.askWhichObjectCard();
				objectCard = currentPlayer.drawCard(key);  // card is removed from the player's hand	
				endObjectCard = true;
				
			} catch (CardNotPresentException e) {	//CardNotExistingException
				endObjectCard = false;
			}
		} while (!endObjectCard);
	}
	
	@Override
	public void initialize() {
		reporter = UserMessagesReporter.getReporterInstance(currentPlayer);
		currentPlayer.startTurn();	//sets Player.hasMoved to false and resets past states
	}

	@Override
	public void turnBeforeMove() {
		if (reporter.askIfObjectCard("Do you want to play an object card before moving?")) {
			playObjectCard();
		}
	}

	@Override
	public void turnMove() {
		
		do {
			try {
				moveCommand = reporter.askForMovement();
				cellAction = moveCommand.execute(currentPlayer, map);
				correctInput = true;
			} catch (Exception e) {	//DestinationUnreachableException, PlayerCanNotEnterException, CellNotExisting
				correctInput = false;
				}
			} while (!correctInput);
	}
	
	@Override
	public void turnLand() {
		try {
			currentPlayer.searchForCard(StringRes.getString("cardKeys.attack"));
			if (reporter.askIfAttack()) {
				objectCard = currentPlayer.drawCard(StringRes.getString("cardKeys.attack"));  // the Attack card is removed from the player's hand
				objectCardAction = objectCard.getObjectAction();
				objectCardAction.execute((Human)currentPlayer, map);
				currentPlayer.setHasAttacked();
			}
		}
		catch (CardNotPresentException e) {
			//do nothing, do not ask the Human Player if he wants to attack, since he doesn't have an Attack Card
		}
		
		if(((Human)currentPlayer).hasSedatives() || currentPlayer.hasAttacked())
		{}
		else{
			cardAction = cellAction.execute(currentPlayer, map);
		
			if (cardAction.hasObjectCard()) {
				cardAction = new DrawObjectCard();
				cardAction.execute(currentPlayer, map);
			}
		}
	}

	@Override
	public void turnAfterMove() {
	
		if (currentPlayer.getMyHand().isOverFull()) {  // too many cards in my hand
			if (reporter.askPlayCardOrDiscard()) {  // user chose "play"
				playObjectCard();
			}
			else {  // user chose "discard"
				discardObjectCard();
			}
		}
		else {  // normal circumstances
			if (reporter.askIfObjectCard("Do you want to play an object card after moving?")) {
				playObjectCard();
			}
		}
	}
	
	@Override
	public void deInitialize() {
		// if we didn't enable it first, this won't do anything
		reporter.stopFillingDefault();
	}

	/**This function is called by the thread that runs inside the TimeController class.
	 * Meanwhile, the thread of ExecutiveController&TurnHandler is stagnating somewhere... 
	 */
	@Override
	public void fillInDefaultChoices() {
		reporter.fillinDefaultOnce();  // free us from the block we're currently stuck in
		reporter.fillinDefaultAlways();  // free us from future blocks
	}

}
