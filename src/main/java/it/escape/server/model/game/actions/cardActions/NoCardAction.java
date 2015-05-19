package it.escape.server.model.game.actions.cardActions;

import it.escape.server.model.game.players.Player;

public class NoCardAction implements CardAction {

	public void execute(Player currentPlayer) {
		
	}

	public boolean hasObjectCard() {
		return false;
	}

}
