package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoiseHereWithObject;

public class NoiseHereCardWithObject implements Card {

	public CardAction getCardAction() {
		return new NoiseHereWithObject();
	}
	
}
