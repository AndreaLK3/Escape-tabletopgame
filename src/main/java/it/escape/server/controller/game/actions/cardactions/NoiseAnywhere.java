package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;

public class NoiseAnywhere implements CardAction{

	public void execute(Player currentPlayer, MapActionInterface map) {
			String location = UserMessagesReporter.getReporterInstance(currentPlayer).askForNoisePosition(map.getPlayerAlphaNumPosition(currentPlayer));
			
			Announcer.getAnnouncerInstance().announceNoise(location);
	}

	public boolean hasObjectCard() {
		return false;
	}



}
