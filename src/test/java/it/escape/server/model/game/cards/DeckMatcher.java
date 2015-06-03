package it.escape.server.model.game.cards;

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
			for (Card c : secondDeck.theDeck)	
			{	c = secondDeck.drawCard();
				if (!((Deck) arg0).theDeck.remove(c))
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
