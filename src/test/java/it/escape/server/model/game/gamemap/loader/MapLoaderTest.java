package it.escape.server.model.game.gamemap.loader;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.GameTypes;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.DangerousCell;
import it.escape.server.model.game.gamemap.EscapeCell;
import it.escape.server.model.game.gamemap.SafeCell;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Test;

public class MapLoaderTest {

	@Test
	public void testLoader() {
		try {
			List<Cell> loadedCells = new ArrayList<Cell>();
			List<Cell> testCells = assignTestCells();
			MapLoader loader = new MapLoader(FilesHelper.getResourceFile("resources/test_map.json"));
			
			assertThat(loader, is("prova",new Position2D(25,14))); // check map's name and size
			for (Cell lc : loader) {
				loadedCells.add(lc);  // gather loaded cells
			}
			for (Cell tc : testCells) {
				assertThat(tc, isContainedIn(loadedCells)); // check if all the testing cells are included
			}
			assertThat(loadedCells, hasHumanStartingCell(new PositionCubic(11, 3, -14))); // check if the starting cells are included
			assertThat(loadedCells, hasAlienStartingCell(new PositionCubic(11, 1, -12)));
			
			
		} catch (BadJsonFileException e) {
			fail("invalid json in test map");
		} catch (IOException e) {
			fail("cannot open test map");
		}
	}
	
	private Matcher<? super MapLoader> is(String name, Position2D size) {
		return new MapLoaderGlobalsmatcher(name, size);
	}
	
	private Matcher<? super Cell> isContainedIn(List<Cell> cells) {
		return new MapLoaderNonStartingCellsMatcher(cells);
	}
	
	private Matcher<? super List<Cell>> hasHumanStartingCell(PositionCubic location) {
		return new MapLoaderStartingCellsMatcher(location,PlayerTeams.HUMANS);
	}
	
	private Matcher<? super List<Cell>> hasAlienStartingCell(PositionCubic location) {
		return new MapLoaderStartingCellsMatcher(location,PlayerTeams.ALIENS);
	}
	
	private List<Cell> assignTestCells() {  // fill up the testing cells' array (note: do not include starting cells here)
		List<Cell> ret = new ArrayList<Cell>();
		ret.add(new DangerousCell(new PositionCubic(0, 2, -2)));
		ret.add(new SafeCell(new PositionCubic(0, 2, -2)));
		ret.add(new EscapeCell(new PositionCubic(0, 2, -2), new GameMode(GameTypes.BASE)));
		
		return ret;
	}

}
