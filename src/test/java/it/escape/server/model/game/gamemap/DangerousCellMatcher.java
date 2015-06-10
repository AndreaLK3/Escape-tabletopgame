package it.escape.server.model.game.gamemap;

import it.escape.server.controller.game.actions.cellactions.GetSectorCardAction;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class DangerousCellMatcher extends BaseMatcher<Object> {

	public boolean matches(Object item) {
		if (item instanceof GetSectorCardAction) {
			return true;
		} 
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("not a GetSectorCardAction");
	}

}
