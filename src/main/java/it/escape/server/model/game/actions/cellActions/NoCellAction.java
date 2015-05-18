package it.escape.server.model.game.actions.cellActions;

import it.escape.server.model.game.actions.cardActions.CardAction;
import it.escape.server.model.game.actions.cardActions.NoCardAction;
import it.escape.server.model.game.character.Player;

public class NoCellAction implements CellAction {

	public CardAction execute(Player currentPlayer) {
		return new NoCardAction();
	}

}
