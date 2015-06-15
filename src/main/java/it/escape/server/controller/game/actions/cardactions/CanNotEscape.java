package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.strings.StringRes;

public class CanNotEscape  implements CardAction{


	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		UserMessagesReporter.getReporterInstance(currentPlayer).
			relayMessage(StringRes.getString("messaging.EscapeHatchDoesNotWork"));
		
	}

	public boolean hasObjectCard() {
		return false;
	}

}
