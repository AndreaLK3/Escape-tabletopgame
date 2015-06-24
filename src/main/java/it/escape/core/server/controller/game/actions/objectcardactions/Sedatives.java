package it.escape.core.server.controller.game.actions.objectcardactions;

import it.escape.core.server.controller.game.actions.HumanActionInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.ObjectCardAction;

public class Sedatives implements ObjectCardAction {

	@Override
	public void execute(HumanActionInterface currentPlayer, MapActionInterface map) {
		currentPlayer.setSedatives();
	}

}
