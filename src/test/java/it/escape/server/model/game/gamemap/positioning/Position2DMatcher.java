package it.escape.server.model.game.gamemap.positioning;

import it.escape.core.server.model.game.gamemap.positioning.Position2D;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class Position2DMatcher extends BaseMatcher<Object> {

	private Position2D riferimento;
	
	public Position2DMatcher(Position2D riferimento) {
		super();
		this.riferimento = riferimento;
	}

	public boolean matches(Object arg0) {
		if (arg0 instanceof Position2D) {
			Position2D candidate = (Position2D) arg0;
			if (candidate.equals(riferimento)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public void describeTo(Description desc) {
		desc.appendText("Coordinates mismatch");
	}

	
	
}
