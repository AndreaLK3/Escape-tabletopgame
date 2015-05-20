package it.escape.server.controller.game.actions.cardActions;

import it.escape.server.model.game.players.Player;

public interface CardAction {
 
	public void execute(Player currentPlayer);
	
	public boolean hasObjectCard();
}
