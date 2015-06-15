package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.Shorthand;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;

public class NoiseAnywhere implements CardAction{

	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
			UserMessagesReporter reporter = UserMessagesReporter.getReporterInstance(currentPlayer);
			
			String location = reporter.askForNoisePosition(map.getPlayerAlphaNumPosition(currentPlayer));
			
			Shorthand.announcer(currentPlayer).announceNoise(location);
	}

	public boolean hasObjectCard() {
		return false;
	}



}
