package it.escape.core.server.model.game.cards.sectorcards;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.cardactions.SilenceWithObject;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.SectorCard;

public class SilenceCardWithObject implements SectorCard, Card {

	public CardAction getCardAction() {
		return new SilenceWithObject();
		
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof SilenceCardWithObject) {
			return true;
		}
		else {
			return false;
		}
	}
}
