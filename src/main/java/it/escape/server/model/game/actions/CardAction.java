package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;

public interface CardAction {
 
	public void execute (Player currentPlayer);
}
