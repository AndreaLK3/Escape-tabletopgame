package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.card.actions.NoiseAnywhereWithObject;

public class NoiseAnywhereCardWithObject {

	public CardAction getCardAction() {
		return new NoiseAnywhereWithObject();		
	}
	
}
