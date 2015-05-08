package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.cards.Card;

public class ClosedShuttle extends ShuttleState {

	@Override
	public boolean tryHatch() {
		System.out.println("Oh no!, this escape hatch is broken! You can't use it.");
		return false;
	}

	@Override
	ShuttleState decideState(Card aCard) {
		
		return this;
	}

}
