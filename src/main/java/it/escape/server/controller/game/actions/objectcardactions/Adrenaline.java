package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.players.Human;

public class Adrenaline implements ObjectCardAction {

	public void execute(Human currentPlayer, MapActionInterface map) {
		currentPlayer.setAdrenaline();

	}

}
