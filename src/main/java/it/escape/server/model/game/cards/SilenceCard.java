package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.card.actions.Silence;

public class SilenceCard implements Card {

	
	public CardAction getCardAction() {
		return new Silence();
		
	}

}
