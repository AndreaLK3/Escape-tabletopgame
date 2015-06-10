package it.escape.server.controller;


import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.cards.objectcards.AdrenalineCard;
import it.escape.server.model.game.cards.objectcards.LightsCard;
import it.escape.server.model.game.cards.objectcards.SedativesCard;
import it.escape.server.model.game.cards.objectcards.TeleportCard;
import it.escape.server.model.game.exceptions.CardNotPresentException;
import it.escape.server.model.game.exceptions.WrongCardException;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

public class TurnHandlerHuman extends TurnHandler {
	
	private ObjectCardAction objectCardAction;
	
	
	public TurnHandlerHuman(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		super(map, deck);
		this.currentPlayer=(Human)currentPlayer;
	}
	
	private void playObjectCard() {
		do {
			try {
				String key = reporter.askWhichObjectCard();
				if (key.equals("none")) {  // only used by the override mechanism
					return;
				}
				objectCard = currentPlayer.drawCard(key);  // this card is removed from the player's hand									   			
				LOG.finer("User selected card: " + objectCard.getClass().getSimpleName());
				
					if (canPlayObjectCard(objectCard)) {
						objectCardAction = objectCard.getObjectAction();
						Shorthand.announcer(currentPlayer).announceObjectCard(currentPlayer, objectCard); 
						objectCardAction.execute((Human)currentPlayer, map);
						endObjectCard = true;
					} else {
						currentPlayer.getMyHand().addCard(objectCard);	//return the card to the Player's hand
						throw new WrongCardException(StringRes.getString("messaging.exceptions.wrongCard"));
					}
			} catch (WrongCardException e) {
				String exceptionMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
				LOG.finer(exceptionMessage);
				reporter.relayMessage(exceptionMessage);
				if (reporter.askIfObjectCard("Do you want to play an object card?")) {
					endObjectCard = false;
				} else {
					endObjectCard = true; 
				}
			} catch (CardNotPresentException e) {	
				String exceptionMessage = e.getClass().getSimpleName();
				LOG.finer(exceptionMessage);
				reporter.relayMessage(exceptionMessage);
				if (reporter.askIfObjectCard("Do you want to play an object card?")) {
					endObjectCard = false;
				} else {
					endObjectCard = true;
				}
			}
		} while (!endObjectCard);
	}
	
	/**This method checks if a Player is actually allowed to play a 
	 * certain ObjectCard, depending on the current phase of the turn.
	 * It is currently public for testing purposes*/
	public boolean canPlayObjectCard(ObjectCard objectCard) {
		
		if (currentPlayer.HasMoved()) {		//after the move
			if (objectCard instanceof TeleportCard || objectCard instanceof LightsCard) {
				return true;
			} else {
				return false;
			}
		} else {	//before the move
			if ( objectCard instanceof SedativesCard || objectCard instanceof AdrenalineCard 
				|| objectCard instanceof TeleportCard || objectCard instanceof LightsCard) {
				return true;
			} else {
				return false;
			}
				
		}
	}
	
	
	//discardObjectCard is defined inside the superclass TurnHandler, since it is the same for Humans and Aliens;
	
	
	@Override
	public void initialize() {
		reporter = UserMessagesReporter.getReporterInstance(currentPlayer);
		currentPlayer.startTurn();	//sets Player.hasMoved to false and resets past states
	}

	@Override
	public void turnBeforeMove() {
		if (currentPlayer.hasCards()) {
			if (reporter.askIfObjectCard(StringRes.getString("messaging.askPlayObjCardBeforeMove"))) {
				playObjectCard();
			}
		}
	}

	
	@Override
	public void turnLand() {
		try {
			currentPlayer.searchForCard(StringRes.getString("cardKeys.attackOrder"));
			if (reporter.askIfAttack()) {
				objectCard = currentPlayer.drawCard(StringRes.getString("cardKeys.attackOrder"));  // the Attack card is removed from the player's hand
				objectCardAction = objectCard.getObjectAction();
				objectCardAction.execute((Human)currentPlayer, map);
				currentPlayer.setHasAttacked();
			}
		} catch (CardNotPresentException e) {
			//do nothing, do not ask the Human Player if he wants to attack, since he doesn't have an Attack Card
		}
		
		if ((!((Human)currentPlayer).hasSedatives()) && !currentPlayer.hasAttacked()) {
			commonLandingLogic();
		}
		
	}

	@Override
	public void turnAfterMove() {
	
		if (currentPlayer.getMyHand().isOverFull()) {  // too many cards in my hand
			if (reporter.askPlayCardOrDiscard()) {  // user chose "play"
				playObjectCard();
			} else {  // user chose "discard"
				discardObjectCard();
			}
		}
		else {  // normal circumstances
			if (currentPlayer.hasCards()) {
				if (reporter.askIfObjectCard(StringRes.getString("messaging.askPlayObjCardBeforeMove"))) {
					playObjectCard();
				}
			}
		}
	}
	
	@Override
	public void deInitialize() {
		// if we didn't enable it first, this won't do anything
		reporter.stopFillingDefault();
	}

	

}
