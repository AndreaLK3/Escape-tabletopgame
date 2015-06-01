package it.escape.server.controller.game.actions.cardactions;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.DecksHandlerInterface;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Player;
import it.escape.utils.Shorthand;

public class NoiseHere implements CardAction{

	public void execute(Player currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		String location = map.getPlayerAlphaNumPosition(currentPlayer);
		
		Shorthand.announcer(currentPlayer).announceNoise(location);

	}

	public boolean hasObjectCard() {
		return false;
	}

}
