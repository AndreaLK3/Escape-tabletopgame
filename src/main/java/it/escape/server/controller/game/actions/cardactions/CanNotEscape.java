package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;

public class CanNotEscape  implements CardAction{


	public void execute(Player currentPlayer, MapActionInterface map) {
		UserMessagesReporter.getReporterInstance(currentPlayer).
			sendMessage(StringRes.getString("messaging.EscapeHatchDoesNotWork"));
		
	}

	public boolean hasObjectCard() {
		return false;
	}

}
