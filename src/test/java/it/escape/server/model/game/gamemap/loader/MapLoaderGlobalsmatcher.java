package it.escape.server.model.game.gamemap.loader;

import it.escape.core.server.model.game.gamemap.loader.MapLoader;
import it.escape.core.server.model.game.gamemap.positioning.Position2D;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class MapLoaderGlobalsmatcher extends BaseMatcher<Object> {

	private String name;
	
	private Position2D size;
	
	public MapLoaderGlobalsmatcher(String name, Position2D size) {
		this.name = name;
		this.size = size;
	}

	public boolean matches(Object item) {
		if (item instanceof MapLoader) {
			MapLoader candidate = (MapLoader) item;
			if (candidate.getMapName().equals(name) &&
					candidate.getMapSize().equals(size)) {
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

	public void describeTo(Description description) {
		description.appendText("global map parameter mismatch");
	}

}
