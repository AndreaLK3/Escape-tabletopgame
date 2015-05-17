package it.escape.server.model.game.cards;

import it.escape.server.model.game.actions.cardActions.CardAction;
import it.escape.server.model.game.actions.cardActions.Silence;

public class SilenceCard implements Card {

	
	public CardAction getCardAction() {
		return new Silence();
		
	}

}
