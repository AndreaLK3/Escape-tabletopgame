package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoiseHereWithObject;
import it.escape.server.model.game.cards.Card;

public class NoiseHereCardWithObject implements Card {

	public CardAction getCardAction() {
		return new NoiseHereWithObject();
	}
	
}
