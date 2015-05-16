package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class MapLoaderStartingCellsMatcher extends BaseMatcher<List<Cell>> {
	
	private PositionCubic location;
	
	private PlayerTeams team;

	public MapLoaderStartingCellsMatcher(PositionCubic location, PlayerTeams team) {
		this.location = location;
		this.team = team;
	}

	public boolean matches(Object item) {
		if (item instanceof List<?>) {
			List<Cell> candidate = (List<Cell>) item;
			for (Cell c : candidate) {
				if (c instanceof StartingCell) {
					StartingCell current = (StartingCell) c;
					if (current.getPosition().equals(location) && current.getType() == team) {
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
		description.appendText("missing starting cell for " + team.toString());
	}
	

}
