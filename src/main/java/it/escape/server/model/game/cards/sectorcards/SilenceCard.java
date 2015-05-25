package it.escape.server.model.game.cards.sectorcards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.Silence;
import it.escape.server.model.game.cards.Card;

public class SilenceCard implements Card {

	
	public CardAction getCardAction() {
		return new Silence();
		
	}

}
