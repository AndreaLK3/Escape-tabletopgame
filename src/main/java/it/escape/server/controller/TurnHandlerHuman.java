package it.escape.server.controller;


import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.cardactions.DrawObjectCard;
import it.escape.server.model.game.Announcer;
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
	
	//discardObjectCard is defined inside the superclass TurnHandler, since it is the same for Humans and Aliens;
	
	
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
	public void turnLand() {
		try {
			currentPlayer.searchForCard(StringRes.getString("cardKeys.attack"));
			if (reporter.askIfAttack()) {
				objectCard = currentPlayer.drawCard(StringRes.getString("cardKeys.attackOrder"));  // the AttackOrder card is removed from the player's hand
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

	

}
