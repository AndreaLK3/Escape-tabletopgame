package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.Shorthand;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.strings.StringRes;

public class Escape implements CardAction {
	
	/**This method:
	 * invokes the method setEscaped() inside the Player to set the boolean variable Escaped to true.
	 * invokes reporter.youEscaped() to send a congratulation to the escaped Player.
	 * invokes announceEscape(currentPlayer) in the Announcer. 
	 * */
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
			currentPlayer.setEscaped();
			UserMessagesReporter.getReporterInstance(currentPlayer).reportSuccessfulEscape();
			Shorthand.announcer(currentPlayer).announceEscape(currentPlayer);
	}

	public boolean hasObjectCard() {
		return false;
	}

}
