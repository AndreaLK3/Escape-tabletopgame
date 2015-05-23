package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoiseAnywhereWithObject;

public class NoiseAnywhereCardWithObject implements Card {

	public CardAction getCardAction() {
		return new NoiseAnywhereWithObject();		
	}
	
}
