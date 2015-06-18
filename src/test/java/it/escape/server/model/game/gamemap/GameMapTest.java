package it.escape.server.model.game.gamemap;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.exceptions.PlayerCanNotEnterException;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.utils.FilesHelper;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameMapTest {
	
	GameMap testMap;
	
	@Before
	public void initializeMap() {
		try {
			testMap = new GameMap(FilesHelper.mapFileFromName("Test_map"));
		} catch (BadJsonFileException e) {
			fail("problem loading test map");
		} catch (IOException e) {
			fail("problem loading test map");
		} catch (MalformedStartingCells e) {
			fail("problem loading test map");
		}
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void pathFindingTest() throws DestinationUnreachableException {
		try {
			Player alien = new Alien("ridley");
			Player alien2 = new Alien("kraid");
			Player alien3 = new Alien("brain");
			
			testMap.addNewPlayer(alien, PlayerTeams.ALIENS);
			testMap.addNewPlayer(alien2, PlayerTeams.ALIENS);
			testMap.addNewPlayer(alien3, PlayerTeams.ALIENS);
			
			CellAction motion = testMap.move(alien, "K06");
			assertThat(motion,isDrawSectorCard());
			CellAction motion3 = testMap.move(alien3, "M06");
			assertThat(motion3,isDrawSectorCard());
			exception.expect(DestinationUnreachableException.class);
			testMap.move(alien2, "C01");
			
		} catch (BadCoordinatesException e) {
			fail("could not move");
		} catch (CellNotExistsException e) {
			fail("could not move");
		} catch (PlayerCanNotEnterException e) {
			fail("could not move");
		} 
	}
	
	@Test
	public void noEntryTest() throws PlayerCanNotEnterException {
		try {
			Player randomAlien = new Alien("jabba");	
			testMap.addNewPlayer(randomAlien, PlayerTeams.ALIENS);
			
			exception.expect(PlayerCanNotEnterException.class);
			testMap.move(randomAlien, "L05");

		} catch (BadCoordinatesException e) {
			fail("could not move");
		} catch (CellNotExistsException e) {
			fail("could not move");
		} catch (DestinationUnreachableException e) {
			fail("could not move");
		} 
	}
	
	private Matcher<? super CellAction> isDrawSectorCard() {
		return new DangerousCellMatcher();
	}
	
	@Test
	public void testCellNotExisting() throws CellNotExistsException {
		try {
			Player randomAlien = new Alien("jabba");	
			testMap.addNewPlayer(randomAlien, PlayerTeams.ALIENS);
			
			exception.expect(CellNotExistsException.class);
			testMap.move(randomAlien, "A12");

		} catch (BadCoordinatesException e) {
			fail("could not move");
		} catch (PlayerCanNotEnterException e) {
			fail("could not move");
		} catch (DestinationUnreachableException e) {
			fail("could not move");
		}
	} 
	
	
}
