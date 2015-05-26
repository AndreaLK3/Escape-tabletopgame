package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoiseAnywhere;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.SectorCard;

public class NoiseAnywhereCard implements Card, SectorCard {


	public CardAction getCardAction() {
		return new NoiseAnywhere();		
	}

}
