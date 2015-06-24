package it.escape.core.server.controller.game.actions.cardactions;

import it.escape.core.server.controller.Shorthand;
import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;

public class Escape implements CardAction {
	
	/**This method:
	 * invokes the method setEscaped() inside the Player to set the boolean variable Escaped to true.
	 * invokes reporter.youEscaped() to send a congratulation to the escaped Player.
	 * invokes announceEscape(currentPlayer) in the Announcer. 
	 * */
	@Override
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
			currentPlayer.setEscaped();
			UserMessagesReporter.getReporterInstance(currentPlayer).reportSuccessfulEscape();
			Shorthand.announcer(currentPlayer).announceEscape(currentPlayer);
			Shorthand.announcer(currentPlayer).announceEscapePodUnavailable(map.getPlayerAlphaNumPosition(currentPlayer));
	}

	public boolean hasObjectCard() {
		return false;
	}

}
