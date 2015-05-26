package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.SilenceWithObject;
import it.escape.server.model.game.cards.SectorCard;

public class SilenceCardWithObject implements SectorCard {

	public CardAction getCardAction() {
		return new SilenceWithObject();
		
	}

}
