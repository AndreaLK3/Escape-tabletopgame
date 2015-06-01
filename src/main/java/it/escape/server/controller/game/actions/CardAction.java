package it.escape.server.controller.game.actions;


public interface CardAction {
 
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck);
	
	public boolean hasObjectCard();
}
