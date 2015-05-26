package it.escape.server.controller;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.playercommands.Attack;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Player;

public class TurnHandlerAlien extends TurnHandler{

	Alien currentPlayer;
	
	
	public TurnHandlerAlien(Player currentPlayer, MapActionInterface map) {
		super(map);
		this.currentPlayer=(Alien)currentPlayer;
	}


	@Override
	public void initialize() {
		reporter = UserMessagesReporter.getReporterInstance(currentPlayer);
		currentPlayer.startTurn();	//sets Player.hasMoved and Player.hasAttacked to false and resets past states
	}
	
	@Override
	public void turnBeforeMove() {}


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
		if (reporter.askIfAttack()) {
			new Attack().execute(currentPlayer, map);
			currentPlayer.setHasAttacked();
		}
		return;
	}


	@Override
	public void turnAfterMove() {
		if (currentPlayer.getMyHand().isOverFull()) {  // too many cards in my hand
			super.discardObjectCard();
		}
		return;
	}



	@Override
	public void fillInDefaultChoices() {
		
		
	}

	@Override
	public void deInitialize() {
		// if we didn't enable it first, this won't do anything
		reporter.stopFillingDefault();
	}
}
