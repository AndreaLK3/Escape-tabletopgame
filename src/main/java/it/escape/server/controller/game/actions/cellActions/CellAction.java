package it.escape.server.controller.game.actions.cellActions;

import it.escape.server.controller.game.actions.cardActions.CardAction;
import it.escape.server.model.game.players.Player;

public interface CellAction {

	public CardAction execute (Player currentPlayer);
	
}
