package it.escape.server.controller.game.actions;

import it.escape.server.model.game.players.Human;

public interface ObjectCardAction {

	public void execute(Human currentPlayer, MapActionInterface map);
}
