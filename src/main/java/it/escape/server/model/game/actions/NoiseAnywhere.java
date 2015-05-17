package it.escape.server.model.game.actions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.model.game.character.Player;

public class NoiseAnywhere implements CardAction{

	public void execute(Player currentPlayer) {
			UserMessagesReporter.getReporterInstance().askForNoise("Where do you want to make a noise?");
	}



}
