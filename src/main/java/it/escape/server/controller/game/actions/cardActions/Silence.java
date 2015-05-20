package it.escape.server.controller.game.actions.cardActions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Player;

public class Silence implements CardAction {

	public void execute(Player currentPlayer, MapActionInterface map) {
		
		
	}

	public boolean hasObjectCard() {
		return false;
	}

}
