package it.escape.server.model.game.actions.cellActions;

import it.escape.server.model.game.actions.cardActions.CardAction;
import it.escape.server.model.game.character.Player;

public interface CellAction {

	public CardAction execute (Player currentPlayer);
	
}
