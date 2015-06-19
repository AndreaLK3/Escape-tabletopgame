package it.escape.core.server.controller.game.actions;


public interface CardAction {
 
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck);
	
	public boolean hasObjectCard();
}
