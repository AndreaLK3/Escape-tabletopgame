package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.cardActions.CardAction;
import it.escape.server.controller.game.actions.cardActions.NoiseHere;

public class NoiseHereCard implements Card {

	
	public CardAction getCardAction() {
		return new NoiseHere();
	}

}
