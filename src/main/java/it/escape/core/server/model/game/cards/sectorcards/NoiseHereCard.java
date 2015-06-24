package it.escape.core.server.model.game.cards.sectorcards;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.cardactions.NoiseHere;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.SectorCard;

public class NoiseHereCard implements Card,SectorCard {

	
	public CardAction getCardAction() {
		return new NoiseHere();
	}

}
