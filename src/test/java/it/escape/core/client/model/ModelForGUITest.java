package it.escape.core.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ModelForGUITest {
	
	ModelForGUI model;
	
	@Before
	public void initialize() {
		model = new ModelForGUI();
	}

	
	/**Tests adding a PlayerState inside the model; 
	 * the PlayerStates are identified by the Player's name.*/
	@Test
	public void testUpdatePlayerExists() {
		assertNull(model.getSpecificPlayerState("Kirk"));
		
		model.updatePlayerExists("Kirk");
		assertNotNull(model.getSpecificPlayerState("Kirk"));
	}
	
	
	/**Tests updating the status inside a PlayerState*/
	@Test
	public void testUpdatePlayerStatus() {
		PlayerState playerState;
		
		model.updatePlayerStatus("Spock", CurrentPlayerStatus.CONNECTED);
		playerState = model.getSpecificPlayerState("Spock");
		
		assertEquals(CurrentPlayerStatus.CONNECTED, playerState.getMyStatus());
		assertNotEquals(CurrentPlayerStatus.WINNER, playerState.getMyStatus());
	}
	
	
	/**Checks: 1) giving another name to an existing playerState.
	 * 2) creating another PlayerState if there was no one with the "previous name".*/
	@Test
	public void testUpdatePlayerRename() {
		model.updatePlayerExists("Uno");
	
		model.updatePlayerRename("Uno", "Due");
		assertNull(model.getSpecificPlayerState("Uno"));
		assertNotNull(model.getSpecificPlayerState("Due"));
		
		assertNull(model.getSpecificPlayerState("Alpha"));
		model.updatePlayerRename("Alpha", "Beta");
		assertNotNull(model.getSpecificPlayerState("Beta"));
	}
	
	
	/**Initialization: Add several playerstates, and then set their status as winners/losers
	 * using the method addWinners(...) in VictoryState. 
	 * Test: the method finalRefreshPlayerStatus must update correctly the status of each player
	 * (in the game, they will be displayed by the Swing GUI).*/
	@Test
	public void testFinalRefreshPlayerStatus() {
		String playerNames[] = {"Alice, Bob, Charles","Xylo, Ypre, Zondag"};
		for (String name : playerNames) {
			model.updatePlayerExists(name);
		}
		VictoryState finalState = model.getVictoryState();
		List<PlayerState> playerStates = model.getPlayerStates();
		
		finalState.addWinners("humans","Alice, Bob");
		finalState.addWinners("aliens", "Zondag");
		
		model.finalRefreshPlayerStatus();
		
		for (PlayerState p : playerStates) {
			if (finalState.getAlienWinners().contains(p.getMyName()) || finalState.getHumanWinners().contains(p.getMyName())) {
				assertEquals(CurrentPlayerStatus.WINNER, p.getMyStatus());
			} else {
				assertEquals(CurrentPlayerStatus.LOSER, p.getMyStatus());
			}
		}
	}

}
