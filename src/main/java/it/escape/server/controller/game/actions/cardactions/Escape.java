package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.Shorthand;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.strings.StringRes;

public class Escape implements CardAction {
	
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
			currentPlayer.setEscaped();
			UserMessagesReporter.getReporterInstance(currentPlayer).
				relayMessage(StringRes.getString("messaging.EscapedSuccessfully"));
			Shorthand.announcer(currentPlayer).announceEscape(currentPlayer);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
