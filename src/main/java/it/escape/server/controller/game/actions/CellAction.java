package it.escape.server.controller.game.actions;


public interface CellAction {

	public CardAction execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck);
	
}
