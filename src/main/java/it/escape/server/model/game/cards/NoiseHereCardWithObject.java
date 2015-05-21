package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardActions.NoiseHereWithObject;

public class NoiseHereCardWithObject {

	public CardAction getCardAction() {
		return new NoiseHereWithObject();
	}
	
}
