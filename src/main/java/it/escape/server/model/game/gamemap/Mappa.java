package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mappa {
	
	private List characters;
	
	private HashMap<String, Cell> cells;
	
	private DecksHandler decks;
	
	private String name;
	
	private Position2D maxSize;

	public Mappa(String filename) throws BadJsonFileException, IOException {
		characters = new ArrayList<Character>();
		cells = new HashMap<String,Cell>();
		decks = new DecksHandler();
		loadMapFromResourceFile(filename);
	}
	
	private void loadMapFromResourceFile(String filename) throws BadJsonFileException, IOException {
		MapLoader loader = new MapLoader(FilesHelper.getResourceFile(filename));
		name = loader.getMapName();
		maxSize = loader.getMapSize();
		for (Cell c : loader) {
			String alphaNumName = CoordinatesConverter.fromCubicToAlphaNum(c.getPosition());
			cells.put(alphaNumName, c);
		}
	}
	
	public void moveCharacter(Character character, PositionCubic dest) {
		// To be implemented
	}
	
	public void noiseInSector(Cell sector) {
		// To be implemented
	}
}
