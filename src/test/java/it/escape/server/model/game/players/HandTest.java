package it.escape.server.model.game.players;

import static org.junit.Assert.*;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.cards.objectcards.AttackCard;
import it.escape.server.model.game.cards.objectcards.LightsCard;
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
