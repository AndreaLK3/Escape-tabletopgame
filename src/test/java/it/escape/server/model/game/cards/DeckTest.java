package it.escape.server.model.game.cards;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Test;

public class DeckTest {

	Deck myDeckObject = new Deck();
	
	@Test
	public void testShuffleDeck() {
		Deck secondDeckObject = new Deck();
		secondDeckObject.shuffleDeck();
		//assertFalse(sameDisposition(myDeckObject, secondDeckObject));	//check if the decks are identical (they should not be)
		assertThat(myDeckObject, is(secondDeckObject));		//check if the deck contains the same cards after having been shuffled

	}


	/*private boolean sameDisposition(Deck firstDeckObject, Deck secondDeckObject) {
		for (Card c : firstDeckObject.theDeck) {
			if (!sameCard(firstDeckObject.drawCard(),secondDeckObject.drawCard()))
					return false;
		}
		return true;
	}*/




	private Matcher<? super Deck> is(Deck secondDeck) {
		return new DeckMatcher(secondDeck);
	}


	@Test
	public void testDrawCard() {
		for (Card c : myDeckObject.theDeck) {
			assertNotNull(myDeckObject.drawCard());
			assertNotNull(myDeckObject.drawCard().toString());
		}
		
	}



}
