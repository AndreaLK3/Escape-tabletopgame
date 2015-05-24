package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.players.Player;

public class Defense implements ObjectCardAction {

	public void execute(Player currentPlayer, MapActionInterface map) {
		// do nothing, as the card is automatically triggered by an attack
	}

}
