package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.CubicDeltas;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMap {
	
	public static GameMap mapInstance;
	
	private List characters;
	
	private HashMap<String, Cell> cells;		//this hashmap stores pairs such as: <A3,Cell(2,3,5)>
	
	private Cell startAliens = null;
	
	private Cell startHumans = null;
	
	private DecksHandler decks;
	
	private String name;
	
	private Position2D maxSize;

	/** Private constructor for this singleton*/
	private GameMap(String filename) throws BadJsonFileException, IOException {
		characters = new ArrayList<Character>();
		cells = new HashMap<String,Cell>();
		decks = DecksHandler.getDecksHandler();
		loadMapFromResourceFile(filename);
	}
	
	public GameMap createMapInstance(String filename) throws BadJsonFileException, IOException {
		if (mapInstance==null)
			mapInstance = new GameMap(filename);
		return mapInstance;
	}
	
	public GameMap getMapInstance() {
		return mapInstance;
	}
	
	
	/**
	 * @param filename
	 * @throws BadJsonFileException
	 * @throws IOException
	 */	
	private void loadMapFromResourceFile(String filename) throws BadJsonFileException, IOException {
		MapLoader loader = new MapLoader(FilesHelper.getResourceFile(filename));
		name = loader.getMapName();
		maxSize = loader.getMapSize();
		for (Cell c : loader) {
			String alphaNumName = CoordinatesConverter.fromCubicToAlphaNum(c.getPosition());
			cells.put(alphaNumName, c);
			attemptAssignStartingCells(c);
		}
		// TODO : no starting cell exception
	}
	
	private List<Cell> getNeighbors(PositionCubic center) throws CellNotExistsException {
		if (cells.containsKey(CoordinatesConverter.fromCubicToAlphaNum(center))) {
			List<Cell> vicini = new ArrayList<Cell>();
			for (PositionCubic pos : CubicDeltas.getDeltas()) {
				PositionCubic candidate = center.cubeAdd(pos);
				if (cells.containsKey(CoordinatesConverter.fromCubicToAlphaNum(candidate))) {
					vicini.add(cells.get(CoordinatesConverter.fromCubicToAlphaNum(candidate)));
				}
			}
			return vicini;
		}
		else {
			throw new CellNotExistsException();  // a bit of defensive programming here
		}
	}
	
	private void attemptAssignStartingCells(Cell c) {
		if (c instanceof StartingCell) {
			StartingCell partenza = (StartingCell) c;
			if (partenza.type == PlayerTeams.ALIENS) {
				startAliens = c;
			}
			else if (partenza.type == PlayerTeams.HUMANS) {
				startHumans = c;
			}
		}
	}
	
	public void move(Player curPlayer , Position2D pos) {
		
	}
	
	public boolean destinationExisting() {
		return false;
	}
	
	private Cell getDestinationCell(PositionCubic destination) {
		return new SafeCell(new PositionCubic(1,1,1));
	}
}
