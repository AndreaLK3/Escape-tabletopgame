package it.escape.server.controller;

import it.escape.server.controller.game.actions.cellActions.CellAction;
import it.escape.server.controller.game.actions.playerCommands.PlayerCommand;
import it.escape.server.model.game.players.Player;

public class NoCommandAction implements PlayerCommand {

	public CellAction execute(Player currentPlayer) throws Exception {
		
		return null;
	}

}
