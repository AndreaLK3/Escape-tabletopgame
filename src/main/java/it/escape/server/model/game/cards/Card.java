package it.escape.server.model.game.cards;

import it.escape.server.model.game.character.AzioneCarta;

public interface Card {
	
	public void effect(AzioneCarta esecutore);
	
}
