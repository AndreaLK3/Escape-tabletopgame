package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardActions.NoiseHere;

public class NoiseHereCard implements Card {

	
	public CardAction getCardAction() {
		return new NoiseHere();
	}

}
