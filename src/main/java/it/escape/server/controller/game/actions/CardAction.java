package it.escape.server.controller.game.actions;

import it.escape.server.model.game.players.Player;

public interface CardAction {
 
	public void execute(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck);
	
	public boolean hasObjectCard();
}
