package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;

public interface CellAction {

	public CardAction execute (Player currentPlayer);
	
}
