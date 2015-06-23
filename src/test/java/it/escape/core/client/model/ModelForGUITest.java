package it.escape.core.client.model;

import static org.junit.Assert.*;

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
	
	
	@Test
	public void testFinalRefreshPlayerStatus() {
		
	}

}
