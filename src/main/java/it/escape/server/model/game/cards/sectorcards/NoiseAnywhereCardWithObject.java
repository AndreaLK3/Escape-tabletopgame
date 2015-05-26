package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoiseAnywhereWithObject;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.SectorCard;

public class NoiseAnywhereCardWithObject implements Card,SectorCard {

	public CardAction getCardAction() {
		return new NoiseAnywhereWithObject();		
	}
	
}
