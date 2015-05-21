package it.escape.server.controller.game.actions.playercommands;

import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerCommand;
import it.escape.server.model.game.players.Player;

public class NoCommandAction implements PlayerCommand {

	public CellAction execute(Player currentPlayer, MapActionInterface map) throws Exception {
		
		return null;
	}

}
