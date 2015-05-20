package it.escape.server.controller.game.actions.playerCommands;

import it.escape.server.controller.game.actions.cellActions.CellAction;
import it.escape.server.model.game.players.Player;

public interface PlayerCommand {

	public CellAction execute(Player currentPlayer) throws Exception;

}
