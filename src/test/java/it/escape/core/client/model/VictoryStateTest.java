package it.escape.core.client.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class VictoryStateTest {

	VictoryState victoryState;
	
	@Before
	public void initialize() {
		victoryState = new VictoryState();
	}
	
	/**Checks if the team and names of the winning players are stored correctly.*/
	@Test
	public void testAddWinners() {
		victoryState.addWinners("humans","Alice, Bob, Charles");
		assertEquals(victoryState.getHumanWinners(),Arrays.asList("Alice", "Bob", "Charles"));
		victoryState.addWinners("aliens", "Xylo, Ypre, Zondag");
		assertEquals(victoryState.getAlienWinners(),Arrays.asList("Xylo", "Ypre", "Zondag"));
		assertFalse(victoryState.getHumanWinners().equals(Arrays.asList("Xylo", "Ypre", "Zondag")));
	}
	
	/**When a team name of a defeated team is given,
	 * that team must be set as defeated.*/
	@Test
	public void testSetTeamDefeated() {
		victoryState.setTeamDefeated("dogs");
		assertFalse(victoryState.isHumansDefeated());
		assertFalse(victoryState.isAliensDefeated());
		
		victoryState.setTeamDefeated("humans");
		assertTrue(victoryState.isHumansDefeated());
		victoryState.setTeamDefeated("Aliens");
		assertTrue(victoryState.isAliensDefeated());
	}
	
	

}
