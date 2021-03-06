package it.escape.core.server.controller;

import static org.junit.Assert.assertTrue;
import it.escape.core.server.MapCreator;
import it.escape.core.server.controller.TurnHandlerAlien;
import it.escape.core.server.model.game.cards.DecksHandler;
import it.escape.core.server.model.game.cards.objectcards.AdrenalineCard;
import it.escape.core.server.model.game.cards.objectcards.AttackCard;
import it.escape.core.server.model.game.cards.objectcards.DefenseCard;
import it.escape.core.server.model.game.cards.objectcards.SedativesCard;
import it.escape.core.server.model.game.gamemap.GameMap;
import it.escape.core.server.model.game.players.Alien;
import it.escape.core.server.model.game.players.Player;

import org.junit.Before;
import org.junit.Test;

public class TurnHandlerAlienTest {

	private Player currentPlayer;
	
	private TurnHandlerAlien turnHandlerAlien;
	
	private GameMap map;
	private DecksHandler deck;
	
	@Before
	public void initializeTest() {
		currentPlayer = new Alien("Ackbar");
		acquireCards(currentPlayer);
		String[] maps = {"Galilei"};
		MapCreator mapCreator = new MapCreator(maps);
		map = mapCreator.getMap();
		deck = new DecksHandler();
		turnHandlerAlien = new TurnHandlerAlien(currentPlayer, map, deck);
		turnHandlerAlien.initialize();
	}
	
	@Test
	public void testInform4Cards() {
		assertTrue (currentPlayer.getMyHand().isOverFull());
	}

	/**This method is used to add 4 cards to the hand*/
	private void acquireCards(Player player) {
		 player.getMyHand().addCard(new AttackCard());;
		 player.getMyHand().addCard(new DefenseCard());
		 player.getMyHand().addCard(new SedativesCard());
		 player.getMyHand().addCard(new AdrenalineCard());
	}
	
}
