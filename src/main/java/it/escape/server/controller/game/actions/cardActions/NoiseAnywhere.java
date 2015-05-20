package it.escape.server.controller.game.actions.cardActions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Player;

public class NoiseAnywhere implements CardAction{

	public void execute(Player currentPlayer, MapActionInterface map) {
			UserMessagesReporter.getReporterInstance(currentPlayer).askForNoise();
	}

	public boolean hasObjectCard() {
		return false;
	}



}