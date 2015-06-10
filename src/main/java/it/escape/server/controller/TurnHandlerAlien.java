package it.escape.server.controller;

import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.playercommands.Attack;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;;

public class TurnHandlerAlien extends TurnHandler{
	
	public TurnHandlerAlien(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		super(map, deck);
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
	public void turnLand() {
		if (reporter.askIfAttack()) {
			new Attack().execute(currentPlayer, map);
			currentPlayer.setHasAttacked();
		}
		if (!currentPlayer.hasAttacked()) {
			commonLandingLogic();
		}
	}


	@Override
	public void turnAfterMove() {
		if (currentPlayer.getMyHand().isOverFull()) {  // too many cards in my hand
			reporter.relayMessage(StringRes.getString("messaging.tooManyCards"));
			super.discardObjectCard();
		}
		return;
	}

	@Override
	public void deInitialize() {
		// if we didn't enable it first, this won't do anything
		reporter.stopFillingDefault();
	}
}
