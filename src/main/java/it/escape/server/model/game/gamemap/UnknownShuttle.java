package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.cards.Card;

public class UnknownShuttle extends ShuttleState {

	@Override
	public boolean tryHatch() {
		
		return false;
	}

	@Override
	ShuttleState decideState(Card aCard) {
		
		return this;
	}

}
