package it.escape.core.server.model.game.cards.sectorcards;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.cardactions.NoiseHereWithObject;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.SectorCard;

public class NoiseHereCardWithObject implements Card,SectorCard {

	@Override
	public CardAction getCardAction() {
		return new NoiseHereWithObject();
	}

	
}
