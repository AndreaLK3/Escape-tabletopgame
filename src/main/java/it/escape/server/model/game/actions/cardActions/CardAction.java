package it.escape.server.model.game.actions.cardActions;

import it.escape.server.model.game.character.Player;

public interface CardAction {
 
	public void execute (Player currentPlayer);
}
