package it.escape.server.model.game.cards;

import it.escape.server.model.game.actions.cardActions.CardAction;
import it.escape.server.model.game.actions.cardActions.NoiseAnywhere;

public class NoiseAnywhereCard implements Card {


	public CardAction getCardAction() {
		return new NoiseAnywhere();		
	}

}
