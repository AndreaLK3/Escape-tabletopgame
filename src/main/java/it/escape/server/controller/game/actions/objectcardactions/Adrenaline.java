package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.game.actions.HumanActionInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;

public class Adrenaline implements ObjectCardAction {

	public void execute(HumanActionInterface currentPlayer, MapActionInterface map) {
		currentPlayer.setAdrenaline();

	}

}
