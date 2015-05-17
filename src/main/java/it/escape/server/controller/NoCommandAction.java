package it.escape.server.controller;

import it.escape.server.model.game.actions.CellAction;
import it.escape.server.model.game.actions.PlayerCommand;
import it.escape.server.model.game.character.Player;

public class NoCommandAction implements PlayerCommand {

	public CellAction execute(Player currentPlayer) throws Exception {
		
		return null;
	}

}
