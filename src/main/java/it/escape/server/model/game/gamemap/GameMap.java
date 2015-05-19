package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.actions.cellActions.CellAction;
import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.CubicDeltas;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.strings.StringRes;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMap {
	
	public static GameMap mapInstance;
	
	private List<Player> characters;	//this one nust be initialized by the GameMaster
	
	private HashMap<String, Cell> cells;		//this hashmap stores pairs such as: <A3,Cell(2,3,5)>
	
	private HashMap<Player, Cell> playersPositions;
	
	private Cell startAliens = null;
	
	private Cell startHumans = null;
	
	private String name;
	
	private Position2D maxSize;

	/** Private constructor for this singleton
	 * @throws MalformedStartingCells */
	private GameMap(String filename) throws BadJsonFileException, IOException, MalformedStartingCells {
		characters = new ArrayList<Player>();
		cells = new HashMap<String,Cell>();
		playersPositions = new HashMap<Player, Cell>();
		loadMapFromResourceFile(filename);
	}
	
	public static GameMap createMapInstance(String filename) throws BadJsonFileException, IOException, MalformedStartingCells {
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
	 * @throws MalformedStartingCells 
	 */	
	private void loadMapFromResourceFile(String filename) throws BadJsonFileException, IOException, MalformedStartingCells {
		MapLoader loader = new MapLoader(FilesHelper.getResourceFile(filename));
		name = loader.getMapName();
		maxSize = loader.getMapSize();
		for (Cell c : loader) {
			String alphaNumName = CoordinatesConverter.fromCubicToAlphaNum(c.getPosition());
			cells.put(alphaNumName, c);
			attemptAssignStartingCells(c);
		}
		if (!verifyStartingCells()) {
			throw new MalformedStartingCells(StringRes.getString("gamemap.missingStartingCells"));
		}
	}
	
	private List<Cell> getNeighbors(PositionCubic center) throws CellNotExistsException {
		if (cellExists(center)) {
			List<Cell> vicini = new ArrayList<Cell>();
			for (PositionCubic pos : CubicDeltas.getDeltas()) {
				PositionCubic candidate = center.cubeAdd(pos);
				if (cellExists(candidate)) {
					vicini.add(cells.get(CoordinatesConverter.fromCubicToAlphaNum(candidate)));
				}
			}
			return vicini;
		}
		else {
			throw new CellNotExistsException();  // a bit of defensive programming here
		}
	}
	
	private void attemptAssignStartingCells(Cell c) throws MalformedStartingCells {
		if (c instanceof StartingCell) {
			StartingCell partenza = (StartingCell) c;
			if (partenza.getType() == PlayerTeams.ALIENS) {
				if (startAliens != null) {
					throw new MalformedStartingCells(StringRes.getString("gamemap.multipleAlienStartingCells"));
				}
				startAliens = c;
			}
			else if (partenza.getType() == PlayerTeams.HUMANS) {
				if (startHumans != null) {
					throw new MalformedStartingCells(StringRes.getString("gamemap.multipleHumanStartingCells"));
				}
				startHumans = c;
			}
		}
	}
	
	private boolean verifyStartingCells() {
		if (startAliens == null || startHumans == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/** This is the function that is invoked by the TurnHandler; it calls several subfunctions*/
	public CellAction move(Player curPlayer , String destination) throws Exception {
		PositionCubic dest3D = CoordinatesConverter.fromAlphaNumToCubic(destination);
		
		if (!destinationReachable(curPlayer, dest3D))
			throw new Exception();
		if (!cellExists(dest3D))
			throw new Exception();
		// controllo ostacoli
		//UpdatePlayerPosition(...)
		return getCell(dest3D).getCellAction(); 
	}
	
	
	private boolean destinationReachable(Player curPlayer, PositionCubic dest) throws CellNotExistsException {
		
		if (dest.distanceFrom(getPlayerPosition(curPlayer)) > curPlayer.getMaxRange()) {
			return false;
		} else {
			List<Cell> visited = new ArrayList<Cell>();
			Cell start = getPlayerPosition(curPlayer);
			visited.add(start);
			List<List> fringes = new ArrayList<List>();
			fringes.add( (new ArrayList<Cell>()) );
			fringes.get(0).add(start);
			
			for (int i = 1; i <= curPlayer.getMaxRange(); i++) {
				fringes.add( (new ArrayList<Cell>()) );
				List<Cell> previous = fringes.get(i - 1);
				List<Cell> current = fringes.get(i);
				for (Cell cube : previous) {
					List<Cell> neighbors = getNeighbors(cube.getPosition());
					for (Cell neighbor : neighbors) {
						if (!visited.contains(neighbor) && neighbor.canEnter(curPlayer)) {
							visited.add(neighbor);
							current.add(neighbor);
						}
					}
					
				}
			}
			
			if (fringes.get(curPlayer.getMaxRange()).contains(getCell(dest))) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private Cell getPlayerPosition(Player player) {
		return playersPositions.get(player);
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
		return cells.get(CoordinatesConverter.fromCubicToAlphaNum(pos3D));
	}
	private Cell getCell(String posAlphaNum) {
		return cells.get(posAlphaNum);
	}

	
	public boolean cellExists(PositionCubic destination) {
		return cells.containsKey(CoordinatesConverter.fromCubicToAlphaNum(destination));
	}
}
