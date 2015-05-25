package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoiseHere;
import it.escape.server.model.game.cards.Card;

public class NoiseHereCard implements Card {

	
	public CardAction getCardAction() {
		return new NoiseHere();
	}

}
