package it.escape.server.model.game.actions.playerCommands;

import it.escape.server.model.game.actions.cellActions.CellAction;
import it.escape.server.model.game.character.Player;

public interface PlayerCommand {

	public CellAction execute(Player currentPlayer) throws Exception;

}
