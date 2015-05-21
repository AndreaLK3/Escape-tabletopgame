package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardActions.SilenceWithObject;

public class SilenceCardWithObject {

	public CardAction getCardAction() {
		return new SilenceWithObject();
		
	}

}
