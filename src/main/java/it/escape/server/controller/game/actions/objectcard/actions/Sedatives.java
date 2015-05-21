package it.escape.server.controller.game.actions.objectcard.actions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.ObjectCardAction;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

public class Sedatives implements ObjectCardAction {

	public void execute(Player currentPlayer, MapActionInterface map) {
		Human player = (Human) currentPlayer;
		player.setSedatives();
	}

}
