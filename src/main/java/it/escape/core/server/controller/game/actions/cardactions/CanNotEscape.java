package it.escape.core.server.controller.game.actions.cardactions;

import it.escape.core.server.controller.Shorthand;
import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.tools.strings.StringRes;

public class CanNotEscape  implements CardAction{


	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		UserMessagesReporter.getReporterInstance(currentPlayer).
			relayMessage(StringRes.getString("messaging.EscapeHatchDoesNotWork"));
		Shorthand.announcer(currentPlayer).announceEscapePodUnavailable(map.getPlayerAlphaNumPosition(currentPlayer));
	}

	public boolean hasObjectCard() {
		return false;
	}

}
