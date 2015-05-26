package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

public class Escape implements CardAction {
	
	public void execute(Player currentPlayer, MapActionInterface map) {
			currentPlayer.setEscaped();
			UserMessagesReporter.getReporterInstance(currentPlayer).
				relayMessage(StringRes.getString("messaging.EscapedSuccessfully"));
			Announcer.getAnnouncerInstance().announceEscape(currentPlayer);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
