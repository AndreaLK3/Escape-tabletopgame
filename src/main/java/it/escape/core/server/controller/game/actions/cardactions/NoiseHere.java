package it.escape.core.server.controller.game.actions.cardactions;

import it.escape.core.server.controller.Shorthand;
import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;

public class NoiseHere implements CardAction{

	@Override
	public void execute(PlayerActionInterface currentPlayer, MapActionInterface map, DecksHandlerInterface deck) {
		String location = map.getPlayerAlphaNumPosition(currentPlayer);
		
		Shorthand.announcer(currentPlayer).announceNoise(location);

	}

	@Override
	public boolean hasObjectCard() {
		return false;
	}

}
