package it.escape.server.model.game.gamemap;

import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.server.model.game.exceptions.MalformedStartingCells;
import it.escape.server.model.game.exceptions.PlayerCanNotEnterException;
import it.escape.server.model.game.gamemap.loader.MapLoader;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.CubicDeltas;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameMap implements MapActionInterface, MapPathfinderInterface {
	
	private Map<String, Cell> cells;		//this hashmap stores pairs such as: <A3,Cell(2,3,5)>
	
	private Map<Player, Cell> playersPositions;
	
	private Cell startAliens = null;
	
	private Cell startHumans = null;
	
	private String name;
	
	private Position2D maxSize;

	/** Constructor
	 * @throws MalformedStartingCells */
	public GameMap(String filename) throws BadJsonFileException, IOException, MalformedStartingCells {
		cells = new HashMap<String,Cell>();
		playersPositions = new HashMap<Player, Cell>();
		loadMapFromResourceFile(filename);
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
	
	public List<Cell> getNeighbors(PositionCubic center) throws CellNotExistsException {
		if (cellExists(center)) {
			List<Cell> vicini = new ArrayList<Cell>();
			for (PositionCubic pos : CubicDeltas.getDeltas()) {
				PositionCubic candidate = center.cubeAdd(pos);
				if (cellExists(candidate)) {
					vicini.add(cells.get(CoordinatesConverter.fromCubicToAlphaNum(candidate)));
				} 
			}
			return vicini;
		} else {
			throw new CellNotExistsException("Cell does not exist");  // a bit of defensive programming here
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
			} else if (partenza.getType() == PlayerTeams.HUMANS) {
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
		}
		return true;
	}
	
	/** This is the function that is invoked by the TurnHandler; it calls several subfunctions
	 * @throws BadCoordinatesException 
	 * @throws DestinationUnreachableException 
	 * @throws CellNotExistsException 
	 * @throws PlayerCanNotEnterException */
	public CellAction move(Player curPlayer , String destination) throws BadCoordinatesException, DestinationUnreachableException, CellNotExistsException, PlayerCanNotEnterException {
		PositionCubic dest3D = CoordinatesConverter.fromAlphaNumToCubic(destination);
		
		if (!dest3D.equals(getPlayerPosition(curPlayer))) {
			// if the destination is the cell we're in, we can bypass all this
			if (!cellExists(dest3D)) {
				throw new CellNotExistsException("Destination cell does not exist");
			}
			Cell c = getCell(dest3D);
			if (!c.canEnter(curPlayer)) {
				throw new PlayerCanNotEnterException("Destination is not accessible");
			}
			if (!destinationReachable(curPlayer, dest3D)) {
				throw new DestinationUnreachableException("Destination is not reachable");
			}
		}
			
		updatePlayerPosition(curPlayer, dest3D);
		
		return getCell(dest3D).getCellAction(); 
	}
	
	public void updatePlayerPosition(Player curPlayer, PositionCubic dest) {
		playersPositions.remove(curPlayer);
		playersPositions.put(curPlayer,getCell(dest));
	}
	
	private boolean destinationReachable(Player curPlayer, PositionCubic dest) throws CellNotExistsException {
		if (dest.distanceFrom(getPlayerPosition(curPlayer)) > curPlayer.getMaxRange()) {
			return false;
		} else {
			PathFinder finder = new PathFinder(this, curPlayer, dest);
			finder.calculateRoute();
			return finder.isReachable();
		}
	}
	
	public Cell getPlayerCell(Player player) {
		return playersPositions.get(player);
	}
	
	public PositionCubic getPlayerPosition(Player player) {
		return playersPositions.get(player).getPosition();
	}
	public String getPlayerAlphaNumPosition(Player player) {
		return CoordinatesConverter.fromCubicToAlphaNum(getPlayerPosition(player));
	}
	
	/**
	 * invoked by GameMaster, add a player (human or alien)
	 * to the game, setting its position to the respective starting cell
	 * @param player
	 * @param team
	 */
	public void addNewPlayer(Player player, PlayerTeams team) {
		Cell startIn = null;
		if (team == PlayerTeams.HUMANS) {
			startIn = startHumans;
		} else {
			startIn = startAliens;
		}
		playersPositions.put(player, startIn);
	}
	
	/**
	 * Returns a list of all player standing in a certain position.
	 * It is assumed that a cell does exist in said position.
	 * @param pos
	 * @return
	 */
	public List<Player> getPlayersByPosition(PositionCubic pos) {
		List<Player> ret = new ArrayList<Player>();
		Cell current = getCell(pos);
		Iterator mapIterator = playersPositions.entrySet().iterator();
		while (mapIterator.hasNext()) {
			Map.Entry pair = (Map.Entry)mapIterator.next();
			Cell candidateCell = (Cell)pair.getValue();
			Player candidatePlayer = (Player)pair.getKey();
			if (pos.equals(candidateCell)) {
				ret.add(candidatePlayer);
			}
		}
		return ret;
	}

	public Cell getStartHumans() {
		return startHumans;
	}
	
	/**Given the position, returns a Cell)
	 * n: REQUIRES : cellExists returns true*/
	public Cell getCell(PositionCubic pos3D) {
		return cells.get(CoordinatesConverter.fromCubicToAlphaNum(pos3D));
	}
	
	private Cell getCell(String posAlphaNum) {
		return cells.get(posAlphaNum);
	}

	/**Check if a Cell exists on the map; returns boolean)*/
	public boolean cellExists(PositionCubic position) {
		return cells.containsKey(CoordinatesConverter.fromCubicToAlphaNum(position));

	}
	
	public boolean cellExists(String position) {
		return cells.containsKey(position);
	}

	public String getName() {
		return name;
	}
	
}
