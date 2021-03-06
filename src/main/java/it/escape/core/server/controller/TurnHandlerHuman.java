package it.escape.core.server.controller;


import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.ObjectCardAction;
import it.escape.core.server.model.game.cards.ObjectCard;
import it.escape.core.server.model.game.cards.objectcards.AdrenalineCard;
import it.escape.core.server.model.game.cards.objectcards.LightsCard;
import it.escape.core.server.model.game.cards.objectcards.SedativesCard;
import it.escape.core.server.model.game.cards.objectcards.TeleportCard;
import it.escape.core.server.model.game.exceptions.CardNotPresentException;
import it.escape.core.server.model.game.exceptions.WrongCardException;
import it.escape.core.server.model.game.players.Human;
import it.escape.core.server.model.game.players.Player;
import it.escape.tools.strings.StringRes;

public class TurnHandlerHuman extends TurnHandler {
	
	private ObjectCardAction objectCardAction;
	
	
	public TurnHandlerHuman(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		super(map, deck);
		this.currentPlayer=(Human)currentPlayer;
	}
	
	private void playObjectCard(String defaultChoice) {
		do {
			try {
				String key = reporter.askWhichObjectCard(defaultChoice);
				if ("none".equals(key)) {  // only used by the override mechanism
					return;
				}
				objectCard = currentPlayer.drawCard(key);  // this card is removed from the player's hand									   			
				LOGGER.finer("User selected card: " + objectCard.getClass().getSimpleName());
				
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
				LOGGER.finer(exceptionMessage);
				reporter.reportCardException(exceptionMessage);
				if (reporter.askIfObjectCard()) {
					endObjectCard = false;
				} else {
					endObjectCard = true; 
				}
			} catch (CardNotPresentException e) {	
				String exceptionMessage = e.getClass().getSimpleName();
				LOGGER.finer(exceptionMessage);
				reporter.relayMessage(exceptionMessage);
				if (reporter.askIfObjectCard()) {
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
	public boolean canPlayObjectCard(ObjectCard candidateObjectCard) {
		
		if (currentPlayer.HasMoved()) {		//after the move
			return canPlayObjectCardAfterMove(candidateObjectCard);
		} else {	//before the move
			return canPlayObjectCardBeforeMove(candidateObjectCard);	
		}
	}
	
	private boolean canPlayObjectCardBeforeMove(ObjectCard candidateObjectCard) {
		if ( candidateObjectCard instanceof SedativesCard || candidateObjectCard instanceof AdrenalineCard 
				|| candidateObjectCard instanceof TeleportCard || candidateObjectCard instanceof LightsCard) {
				return true;
			} else {
				return false;
			}
	}
	
	private boolean canPlayObjectCardAfterMove(ObjectCard candidateObjectCard) {
		if (candidateObjectCard instanceof TeleportCard || candidateObjectCard instanceof LightsCard) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public void initialize() {
		reporter = UserMessagesReporter.getReporterInstance(currentPlayer);
		currentPlayer.startTurn();	//sets Player.hasMoved to false and resets past states
	}

	@Override
	public void turnBeforeMove() {
		if (currentPlayer.hasPlayableCards()) {
			if (reporter.askIfObjectCard()) {
				playObjectCard("none");
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
		
		if (currentPlayer.getMyHand().isOverFull()) {		// too many cards in my hand
			
			if (currentPlayer.hasPlayableCards()){ 	 //I have cards that I can play
			
				if (reporter.askPlayCardOrDiscard()) {  // user chose "play"
				playObjectCard(currentPlayer.getMyHand().getCardName(0));
				} 
				else {  // user chose "discard"
				super.discardObjectCard();
				}
			}
			else {	//I can't play any of the cards I own. I will have to discard some attack/defense
				reporter.reportHaveToDiscard();
				super.discardObjectCard();
			}
		}
		else {  // normal circumstances
			if (currentPlayer.hasPlayableCards()) {
				if (reporter.askIfObjectCard()) {
					playObjectCard("none");
				}
			}
		}
	}
	
	@Override
	public void deInitialize() {
		// if we didn't enable it first, this won't do anything
		reporter.stopFillingDefault();
	}

	@Override
	public boolean mustNotMove() {
		return ((Human)currentPlayer).hasEscaped();
	}

	

}
