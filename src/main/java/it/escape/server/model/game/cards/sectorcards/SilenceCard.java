package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.Silence;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.SectorCard;

public class SilenceCard implements Card,SectorCard {

	
	public CardAction getCardAction() {
		return new Silence();
		
	}

}
