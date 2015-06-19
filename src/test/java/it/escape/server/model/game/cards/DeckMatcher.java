package it.escape.server.model.game.cards;

import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.Deck;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class DeckMatcher extends BaseMatcher<Object> {

	Deck secondDeck;
	
	public DeckMatcher(Deck secondDeck) {
		super();
		this.secondDeck = secondDeck;
	}

	public boolean matches(Object arg0) {
		
		boolean sameCards = true;
		
		if (arg0 instanceof Deck) {
			for (Card c : secondDeck.getTheDeck())	
			{	c = secondDeck.drawCard();
				if (!((Deck) arg0).getTheDeck().remove(c))
					sameCards = false;
			}
		
			if (sameCards)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public void describeTo(Description desc) {
		desc.appendText("The deck does not contain the same cards after it has been shuffled");
		
	}

}
