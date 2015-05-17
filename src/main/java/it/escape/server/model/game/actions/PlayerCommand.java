package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;

public interface PlayerCommand {

	public CellAction execute(Player currentPlayer) throws Exception;

}
