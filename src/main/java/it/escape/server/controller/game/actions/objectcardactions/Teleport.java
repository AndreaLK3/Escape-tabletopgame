package it.escape.server.controller.game.actions.objectcardactions;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.players.Human;

public class Teleport implements ObjectCardAction {

	public void execute(Human currentPlayer, MapActionInterface map) {
		Cell c = map.getStartHumans();
		map.updatePlayerPosition(currentPlayer, c.getPosition());

	}

}
