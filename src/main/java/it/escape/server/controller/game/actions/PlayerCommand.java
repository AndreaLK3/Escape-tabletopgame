package it.escape.server.controller.game.actions;

import it.escape.server.model.game.players.Player;

public interface PlayerCommand {

	public CellAction execute(Player currentPlayer, MapActionInterface map) throws Exception;

}
