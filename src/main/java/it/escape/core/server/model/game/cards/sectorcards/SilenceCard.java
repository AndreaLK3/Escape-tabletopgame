package it.escape.core.server.model.game.cards.sectorcards;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.cardactions.Silence;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.SectorCard;

public class SilenceCard implements Card,SectorCard {

	
	public CardAction getCardAction() {
		return new Silence();
		
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof SilenceCard) {
			return true;
		}
		else {
			return false;
		}
	}

}
