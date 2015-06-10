package it.escape.server.model.game.players;

import static org.junit.Assert.*;
import it.escape.server.model.game.cards.objectcards.AttackCard;
import it.escape.server.model.game.cards.objectcards.TeleportCard;

import org.junit.Before;
import org.junit.Test;

public class HandTest {

	Hand hand;
	
	@Before
	public void initializeTest() {
		hand = new Hand();
		addCards();
	}
	
	private void addCards() {
		hand.addCard(new TeleportCard());
		hand.addCard(new AttackCard());
	}

	@Test
	public void testGetCardName() {
		assertEquals ("teleport", hand.getCardName(0));
		assertEquals ("attack", hand.getCardName(1));
	}

	@Test
	public void testGetCardFromString() {
		assertTrue(hand.getCardFromString("attack") instanceof AttackCard );
	}

}
