package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;

public class DrawEscapeCard implements CellAction {

	public CardAction execute(Player currentPlayer) {
			return new Escape();
	}

}
