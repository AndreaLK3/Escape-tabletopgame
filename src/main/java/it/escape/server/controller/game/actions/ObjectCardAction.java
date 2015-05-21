package it.escape.server.controller.game.actions;

import it.escape.server.model.game.players.Player;

public interface ObjectCardAction {

	public void execute(Player currentPlayer, MapActionInterface map);
}
