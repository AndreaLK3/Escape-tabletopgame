package it.escape.server.model.game.cards;

import it.escape.server.model.game.character.CardAction;

public interface Card {
	
	public void effect(CardAction esecutore);
	
}
