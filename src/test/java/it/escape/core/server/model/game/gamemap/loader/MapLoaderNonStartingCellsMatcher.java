package it.escape.core.server.model.game.gamemap.loader;

import it.escape.core.server.model.game.gamemap.Cell;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class MapLoaderNonStartingCellsMatcher extends BaseMatcher<Cell> {
	
	private List<Cell> cells;

	public MapLoaderNonStartingCellsMatcher(List<Cell> cells) {
		this.cells = cells;
	}

	public boolean matches(Object item) {
		if (item instanceof Cell) {
			Cell candidate = (Cell) item;
			for (Cell c : cells) {
				if (c.getClass().equals(candidate.getClass())) {
					if (c.getPosition().equals(candidate.getPosition())) {
						return true;
					}
				}
			}
			return false;
		}
		else {
			return false;
		}
	}

	public void describeTo(Description description) {
		description.appendText("Missing test cell in loaded map");
	}

}
