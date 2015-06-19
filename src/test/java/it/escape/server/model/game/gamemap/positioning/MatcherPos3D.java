package it.escape.server.model.game.gamemap.positioning;

import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class MatcherPos3D extends BaseMatcher<Object> {

	PositionCubic pos1;
	
	public MatcherPos3D(PositionCubic pos1) {
		this.pos1 = pos1;
	}
	
	public boolean matches(Object pos2) {
		if (pos2 instanceof PositionCubic)
			if (pos1.equals(pos2))
				return true;
			else
				return false;
		else
			return false;
	}

	public void describeTo(Description desc) {
		desc.appendText("Coordinates mismatch trying to convert from (col, row) to (x,y,z)");
		
	}

}
