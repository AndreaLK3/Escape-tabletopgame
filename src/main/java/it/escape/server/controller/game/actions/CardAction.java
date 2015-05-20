package it.escape.server.controller.game.actions;

import it.escape.server.model.game.players.Player;

public interface CardAction {
 
	public void execute(Player currentPlayer, MapActionInterface map);
	
	public boolean hasObjectCard();
}
