package it.escape.server.model.game.gamemap;

import static org.junit.Assert.*;
import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.exceptions.PlayerCanNotEnterException;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.Position2DMatcher;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameMapTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void pathFindingTest() throws DestinationUnreachableException {
		try {
			GameMap testMap = new GameMap("resources/test_map.json");
			Player alien = new Alien("ridley");
			Player alien2 = new Alien("kraid");
			testMap.addNewPlayer(alien, PlayerTeams.ALIENS);
			testMap.addNewPlayer(alien2, PlayerTeams.ALIENS);
			
			CellAction motion = testMap.move(alien, "N06");
			assertThat(motion,isDrawSectorCard());
			exception.expect(DestinationUnreachableException.class);
			testMap.move(alien2, "J06");
			
		} catch (BadJsonFileException e) {
			fail("problem loading test map");
		} catch (IOException e) {
			fail("problem loading test map");
		} catch (MalformedStartingCells e) {
			fail("problem loading test map");
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
			GameMap testMap = new GameMap("resources/test_map.json");
			Player alien = new Alien("jabba");	
			testMap.addNewPlayer(alien, PlayerTeams.ALIENS);
			
			exception.expect(PlayerCanNotEnterException.class);
			testMap.move(alien, "L08");
			
		} catch (BadJsonFileException e) {
			fail("problem loading test map");
		} catch (IOException e) {
			fail("problem loading test map");
		} catch (MalformedStartingCells e) {
			fail("problem loading test map");
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

}
