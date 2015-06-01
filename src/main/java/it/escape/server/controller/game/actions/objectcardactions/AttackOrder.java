package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.game.actions.HumanActionInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.controller.game.actions.playercommands.Attack;

public class AttackOrder implements ObjectCardAction {

	public void execute(HumanActionInterface currentPlayer, MapActionInterface map) {
		new Attack().execute((PlayerActionInterface) currentPlayer, map);
	}

}
