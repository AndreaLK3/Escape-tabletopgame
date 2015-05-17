package it.escape.server.model.game.cards;

import it.escape.server.model.game.actions.CardAction;
import it.escape.server.model.game.actions.NoiseHere;

public class NoiseHereCard implements Card {

	
	public CardAction getCardAction() {
		return new NoiseHere();
	}

}
