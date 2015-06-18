package it.escape.server.controller;

import static org.junit.Assert.*;
import it.escape.server.MapCreator;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.cards.objectcards.AdrenalineCard;
import it.escape.server.model.game.cards.objectcards.AttackCard;
import it.escape.server.model.game.cards.objectcards.DefenseCard;
import it.escape.server.model.game.cards.objectcards.LightsCard;
import it.escape.server.model.game.cards.objectcards.SedativesCard;
import it.escape.server.model.game.cards.objectcards.TeleportCard;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

import org.junit.Before;
import org.junit.Test;

public class TurnHandlerHumanTest {

	private Player currentPlayer;
	
	private TurnHandlerHuman turnHandlerHuman;
	
	private GameMap map;
	private DecksHandler deck;
	
	@Before
	public void initializeTest() {
		currentPlayer = new Human("Kirk");
		acquireCards(currentPlayer);
		String[] maps = {"Galilei"};
		MapCreator mapCreator = new MapCreator(maps);
		map = mapCreator.getMap();
		deck = new DecksHandler();
		turnHandlerHuman = new TurnHandlerHuman(currentPlayer, map, deck);
	}
	
	@Test
	public void testCanPlayObjectCard() {
		
		assertFalse(currentPlayer.HasMoved());
		
		assertFalse(turnHandlerHuman.canPlayObjectCard(new AttackCard()));
		assertFalse(turnHandlerHuman.canPlayObjectCard(new DefenseCard()));
		
		assertTrue(turnHandlerHuman.canPlayObjectCard(new SedativesCard()));
		assertTrue(turnHandlerHuman.canPlayObjectCard(new AdrenalineCard()));
		assertTrue(turnHandlerHuman.canPlayObjectCard(new TeleportCard()));
		assertTrue(turnHandlerHuman.canPlayObjectCard(new LightsCard()));
		
		currentPlayer.setHasMoved(true);
		
		assertFalse(turnHandlerHuman.canPlayObjectCard(new AttackCard()));
		assertFalse(turnHandlerHuman.canPlayObjectCard(new DefenseCard()));
		assertFalse(turnHandlerHuman.canPlayObjectCard(new SedativesCard()));
		assertFalse(turnHandlerHuman.canPlayObjectCard(new AdrenalineCard()));
		
		assertTrue(turnHandlerHuman.canPlayObjectCard(new TeleportCard()));
		assertTrue(turnHandlerHuman.canPlayObjectCard(new LightsCard()));
		
		
		
		
	}
	
	private void acquireCards(Player player) {
		 player.getMyHand().addCard(new AttackCard());;
		 player.getMyHand().addCard(new DefenseCard());
		 player.getMyHand().addCard(new SedativesCard());
		 player.getMyHand().addCard(new AdrenalineCard());
		 player.getMyHand().addCard(new TeleportCard());
		 player.getMyHand().addCard(new LightsCard());
	}

}
