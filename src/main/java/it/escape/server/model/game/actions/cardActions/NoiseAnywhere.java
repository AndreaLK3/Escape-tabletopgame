package it.escape.server.model.game.actions.cardActions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.model.game.players.Player;

public class NoiseAnywhere implements CardAction{

	public void execute(Player currentPlayer) {
			UserMessagesReporter.getReporterInstance().askForNoise("Where do you want to make a noise?");
	}

	public boolean hasObjectCard() {
		return false;
	}



}
