package it.escape.server.controller.game.actions.objectcardactions;

import java.util.List;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.controller.game.actions.playercommands.Attack;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

public class AttackOrder implements ObjectCardAction {

	public void execute(Human currentPlayer, MapActionInterface map) {
		new Attack().execute(currentPlayer, map);
	}

}
