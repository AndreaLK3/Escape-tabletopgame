package it.escape.core.server.model.game.cards;

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
		
		if (arg0 instanceof Deck) {
			Deck firstDeck = (Deck) arg0;
			boolean sameCards = true;
				
			for (Card firstCard : firstDeck.getTheDeck()) {
				if (!existsCard(firstCard, secondDeck)) {
					sameCards = false;
				}
			}
			return sameCards;
		}
		else
			return false;
	}

	
	/**Checks if a card exists inside a deck, using the CardMatcher*/
	private boolean existsCard(Card card, Deck deck) {
		boolean exists = false;
		for (Card secondCard : deck.getTheDeck()) {
			if (new CardMatcher(card).matches(secondCard)) {
				exists = true;
			}
		}
		return exists;
	}
	
	
	public void describeTo(Description desc) {
		desc.appendText("The deck does not contain the same cards after it has been shuffled");
		
	}

}
