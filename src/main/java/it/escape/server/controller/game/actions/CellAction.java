package it.escape.server.controller.game.actions;

import it.escape.server.model.game.players.Player;

public interface CellAction {

	public CardAction execute(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck);
	
}
