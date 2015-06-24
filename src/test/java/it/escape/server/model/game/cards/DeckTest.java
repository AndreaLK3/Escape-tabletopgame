package it.escape.server.model.game.cards;

import static org.junit.Assert.*;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.Deck;
import it.escape.core.server.model.game.cards.ObjectDeck;
import org.junit.Test;

public class DeckTest {

	Deck myDeckObject = new ObjectDeck();
	
	/**Test: 2 decks that are shuffled should have the same cards, but not the same cards disposition.*/
	@Test
	public void testShuffleDeck() {
		Deck copyOfMyDeck = myDeckObject;
		Deck secondDeckObject = new ObjectDeck(); //n: Decks are shuffled when they are created
		assertTrue(sameDisposition(copyOfMyDeck, myDeckObject));
		assertFalse(sameDisposition(myDeckObject, secondDeckObject));	//the 1st and the 2nd deck should not be identical
		assertTrue(isSameDeck(myDeckObject, secondDeckObject));		//check if the deck contains the same cards after having been shuffled

	}


	/**Utility: Checks if the cards in 2 decks are placed in the same order.
	 * n: requires: the decks must have the same number of cards.*/
	private boolean sameDisposition(Deck firstDeck, Deck secondDeck) {
		for (int i = 0; i<firstDeck.size(); i++) {
			if (!isSameCard(firstDeck.getTheDeck().get(i), secondDeck.getTheDeck().get(i))) {
				return false;
			}
		}
		return true;
	}

	private boolean isSameCard(Card firstCard, Card secondCard) {
		return new CardMatcher(firstCard).matches(secondCard);
	}


	private boolean isSameDeck(Deck firstDeck, Deck secondDeck) {
		return new DeckMatcher(secondDeck).matches(firstDeck);
	}




}
