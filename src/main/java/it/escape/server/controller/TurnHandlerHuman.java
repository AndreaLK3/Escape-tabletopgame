package it.escape.server.controller;


import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.cardactions.DrawObjectCard;
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
	
	private void playObjectCard() {
		do {
			try {
				boolean restrictions;
				String key = reporter.askWhichObjectCard();
				objectCard = currentPlayer.drawCard(key);  // this card is removed from the player's hand
				
				if (objectCard != null) {  // the key matches a card
					/*
					 * check restrictions based on whether the player has already moved
					 * during this turn (i.e. can't use sedatives after a move)
					 */
					if (currentPlayer.HasMoved()) {
						restrictions = false;  // control logic goes here
					}
					else {
						restrictions = false;  // control logic goes here
					}
					
					if (!restrictions) {
						objectCardAction = objectCard.getObjectAction();
						// TODO: broadcast (via Announcer) that the user will now use an object card 
						objectCardAction.execute(currentPlayer, map);
						correctInput = true;
					}
					else {
						correctInput = false;
					}
				}
				else {
					correctInput = false;
				}
			} catch (Exception e) {	//CardNotFoundException
				correctInput = false;
			}
		} while (!correctInput);
	}
	
	private void discardObjectCard() {
		do {
			try {
				String key = reporter.askWhichObjectCard();
				objectCard = currentPlayer.drawCard(key);  // card is removed from the player's hand
				
				if (objectCard != null) {  // the key matches a card
					correctInput = true;
				}
				else {
					correctInput = false;
				}
			} catch (Exception e) {	//CardNotExistingException
				correctInput = false;
			}
		} while (!correctInput);
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
	public void fillInDefaultChoices() {
		
	}

}
