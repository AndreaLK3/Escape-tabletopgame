package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;
import it.escape.utils.Shorthand;

public class Escape implements CardAction {
	
	public void execute(Player currentPlayer, MapActionInterface map) {
			currentPlayer.setEscaped();
			UserMessagesReporter.getReporterInstance(currentPlayer).
				relayMessage(StringRes.getString("messaging.EscapedSuccessfully"));
			Shorthand.announcer(currentPlayer).announceEscape(currentPlayer);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
