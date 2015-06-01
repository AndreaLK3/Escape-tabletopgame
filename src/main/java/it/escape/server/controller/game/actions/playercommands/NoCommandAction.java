package it.escape.server.controller.game.actions.playercommands;

import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.controller.game.actions.PlayerCommand;

public class NoCommandAction implements PlayerCommand {

	public CellAction execute(PlayerActionInterface currentPlayer, MapActionInterface map) throws Exception {
		
		return null;
	}

}
