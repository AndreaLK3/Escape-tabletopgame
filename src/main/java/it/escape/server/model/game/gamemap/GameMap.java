package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.actions.cellActions.CellAction;
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
	
	private HashMap<Cell, Player> playersPositions;
	
	private Cell startAliens = null;
	
	private Cell startHumans = null;
	
	private String name;
	
	private Position2D maxSize;

	/** Private constructor for this singleton*/
	private GameMap(String filename) throws BadJsonFileException, IOException {
		characters = new ArrayList<Character>();
		cells = new HashMap<String,Cell>();
		loadMapFromResourceFile(filename);
	}
	
	public static GameMap createMapInstance(String filename) throws BadJsonFileException, IOException {
		if (mapInstance==null)
			mapInstance = new GameMap(filename);
		return mapInstance;
	}
	
	public static GameMap getMapInstance() {
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
	
	/** This is the function that is invoked by the TurnHandler; it calls several subfunctions*/
	public CellAction move(Player curPlayer , Position2D dest2D) throws Exception {
		PositionCubic dest3D = CoordinatesConverter.fromOddqToCubic(dest2D);
		
		if (!destinationInRange(curPlayer, dest3D))
			throw new Exception();
		if (!cellExists(dest3D))
			throw new Exception();
		//UpdatePlayerPosition(...)
		return getCell(dest3D).getCellAction(); 
	}
	
	
	private boolean destinationInRange(Player curPlayer, PositionCubic dest) {
		
		if (dest.distanceFrom(getPlayerPosition(curPlayer)) > curPlayer.getMaxRange())
			return false;
		else
			return true;
	}
	
	private PositionCubic getPlayerPosition(Player player) {
		return new PositionCubic(1,1,1);
	}
	
	/** To be implemented: gets a Cell given the position.
	 * (Nota1: Forse non è necessario creare una nuova cella, si 
	 * può restituire il riferimento a quella già presente)
	 * (Nota2: Forse non è necessario fare le conversioni da AlphaNum a 2D e poi nella mappa da 
	 * 2D a 3D, se hai messo già la lista con <Cell, String>)
	 * @param pos3D
	 * @return Cell
	 */
	private Cell getCell(PositionCubic pos3D) {
		return new DangerousCell(pos3D);
	}
	

	
	public boolean cellExists(PositionCubic destination) {
		return false;
	}
}
