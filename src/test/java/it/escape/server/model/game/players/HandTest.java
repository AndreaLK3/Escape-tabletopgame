package it.escape.server.model.game.players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.escape.core.server.model.game.cards.objectcards.AttackCard;
import it.escape.core.server.model.game.cards.objectcards.LightsCard;
import it.escape.core.server.model.game.cards.objectcards.TeleportCard;
import it.escape.core.server.model.game.players.Hand;

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
		hand.addCard(new LightsCard());
	}

	
	@Test
	public void testGetCardName() {
		assertEquals ("teleport", hand.getCardName(0));
		assertEquals ("attack", hand.getCardName(1));
		assertEquals ("lights", hand.getCardName(2));
	}
	
	
	@Test
	public void testGetAllCardNames() {
		String expectedNames[] = {"teleport","attack","lights"};
		String returnedNames[] = hand.getAllCardNames();
		
		for (int i=0; i<expectedNames.length; i++) {
			assertTrue(expectedNames[i].equals(returnedNames[i]));
		}
		
	}
	
	
	@Test
	public void testGetAllCardNamesAsString() {
		String expectedString = "teleport - attack - lights - ";
		String returnedString = hand.getAllCardNamesAsString();
		assertTrue(expectedString.equals(returnedString));
	}
	

	@Test
	public void testGetCardFromString() {
		assertTrue(hand.getCardFromString("teleport") instanceof TeleportCard );
		assertTrue(hand.getCardFromString("attack") instanceof AttackCard );
		assertTrue(hand.getCardFromString("Lights") instanceof LightsCard );
	}

}
