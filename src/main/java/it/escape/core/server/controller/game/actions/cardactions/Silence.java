package it.escape.core.server.controller.game.actions.cardactions;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;

public class Silence implements CardAction {

	@Override
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {	
		//it doesn't do anything
	}

	@Override
	public boolean hasObjectCard() {
		return false;
	}

}
