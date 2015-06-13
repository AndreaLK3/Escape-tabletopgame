package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.controller.game.actions.HumanActionInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class Teleport implements ObjectCardAction {

	public void execute(HumanActionInterface currentHuman, MapActionInterface map) {
		PlayerActionInterface currentPlayer = (PlayerActionInterface) currentHuman;
		PositionCubic c = map.getStartHumans();
		map.updatePlayerPosition(currentPlayer, c);
		
		// say where I am after the move
		UserMessagesReporterSocket.getReporterInstance(currentPlayer).reportMyUserPosition(
				map.getPlayerAlphaNumPosition(currentPlayer));
	}

}
