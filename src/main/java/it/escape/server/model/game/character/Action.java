package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;

public interface Action {
		
	public Card drawCard();
	
	public void escape();
	
	public void noAction();
}
