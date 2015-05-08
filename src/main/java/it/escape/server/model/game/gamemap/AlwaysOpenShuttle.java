package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.cards.Card;

public class AlwaysOpenShuttle extends ShuttleState {

	@Override
	public boolean tryHatch() {
		System.out.println("You managed to reach an escape shuttle!");
		return true;
	}

	@Override
	ShuttleState decideState(Card aCard) {
		
		return this;
	}

}
