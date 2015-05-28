package it.escape.server.model.game.cards;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import it.escape.server.model.game.cards.objectcards.AdrenalineCard;
import it.escape.server.model.game.cards.objectcards.AttackCard;
import it.escape.server.model.game.cards.objectcards.DefenseCard;
import it.escape.server.model.game.cards.objectcards.LightsCard;
import it.escape.server.model.game.cards.objectcards.SedativesCard;
import it.escape.server.model.game.cards.objectcards.TeleportCard;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverterTest;

import org.junit.Before;
import org.junit.Test;

public class HandTest {

	private static final Logger LOG = Logger.getLogger( HandTest.class.getName() );
	private Hand hand = new Hand();
	private ObjectCard card;
	
	@Before
	public void acquireCards() {
		 hand.handOfCards.add(new AttackCard());
		 hand.handOfCards.add(new DefenseCard());
		 hand.handOfCards.add(new SedativesCard());
		 hand.handOfCards.add(new AdrenalineCard());
		 hand.handOfCards.add(new TeleportCard());
		 hand.handOfCards.add(new LightsCard());
	}
	
	@Test
	public void testGetCardFromString() {
		String array[] = {"Adrenaline","Tele","Lights","Attack","Defense","Sed"};
		for (String s : array)	
		{	card = hand.getCardFromString(s);
			assertNotNull(card);
			LOG.info(card.toString());
		}
		
	}
	
	

}
