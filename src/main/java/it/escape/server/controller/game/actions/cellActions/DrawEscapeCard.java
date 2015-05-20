package it.escape.server.controller.game.actions.cellActions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.cardActions.Escape;
import it.escape.server.model.game.players.Player;

public class DrawEscapeCard implements CellAction {

	public CardAction execute(Player currentPlayer, MapActionInterface map) {
			return new Escape();
	}

}
