package it.escape.server.controller;


import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.objectCards.ObjectCard;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

public class TurnHandlerHuman extends TurnHandler {

	private Human currentPlayer;
	private UserMessagesReporter reporter;
	private ObjectCardAction objectCardAction;
	private ObjectCard objectCard;
	
	public TurnHandlerHuman(Player currentPlayer, MapActionInterface map) {
		super(map);
		this.currentPlayer=(Human)currentPlayer;
	}
	
	public void initialize() {
		reporter = UserMessagesReporter.getReporterInstance(currentPlayer);
		currentPlayer.startTurn();
	}
	
	private void playObjectCard(String message) {
		if (reporter.askIfObjectCard(message)) {
			do {
				try {
					boolean restrictions;
					String key = reporter.askWhichObjectCard();
					objectCard = currentPlayer.drawCard(key);
					
					if (objectCard != null) {  // the key matches a card
						/*
						 * check restrictions based on whether the player has already moved
						 * during this turn (i.e. no can't use sedatives after a move)
						 */
						if (currentPlayer.getHasMoved()) {
							restrictions = false;  // control logic goes here
						}
						else {
							restrictions = false;  // control logic goes here
						}
						
						if (!restrictions) {
							objectCardAction = objectCard.getObjectAction();
							correctInput = true;
						}
						else {
							correctInput = false;
						}
					}
					else {
						correctInput = false;
					}
				} catch (Exception e) {	//DestinationNotInRangeException, DestinationNotExistingException
					correctInput = false;
				}
			} while (!correctInput);
			objectCardAction.execute(currentPlayer, map);
		}
	}

	@Override
	public void turnBeforeMove() {
		playObjectCard("Do you want to play an object card before moving?");
	}

	@Override
	public void turnMove() {
		do {
			try {
				moveCommand = reporter.askForMovement();
				cellAction = moveCommand.execute(currentPlayer, map);
				correctInput = true;
			} catch (Exception e) {	//DestinationNotInRangeException, DestinationNotExistingException
				correctInput = false;
				}
			} while (!correctInput);
	}
	
	@Override
	public void turnLand() {
		if(!currentPlayer.hasSedatives()) {
			cardAction = cellAction.execute(currentPlayer, map);
			if (cardAction.hasObjectCard()) {
				objectCard = (ObjectCard) DecksHandler.getDecksHandler().drawObjectCard();
				if (!currentPlayer.acquireCard(objectCard)) {
					// ask the player to discard one of the four cards
				}
			}
		}
	}

	@Override
	public void turnAfterMove() {
		playObjectCard("Do you wish to play an object card after moving?");
	}


}
