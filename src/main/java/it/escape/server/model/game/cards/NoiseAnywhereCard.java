package it.escape.server.model.game.cards;

import it.escape.server.model.game.actions.CardAction;
import it.escape.server.model.game.actions.NoiseAnywhere;

public class NoiseAnywhereCard implements Card {


	public CardAction getCardAction() {
		return new NoiseAnywhere();		
	}

}
