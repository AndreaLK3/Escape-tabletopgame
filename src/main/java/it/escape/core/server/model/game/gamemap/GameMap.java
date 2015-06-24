package it.escape.core.server.model.game.gamemap;

import it.escape.core.server.controller.game.actions.CellAction;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.exceptions.BadCoordinatesException;
import it.escape.core.server.model.game.exceptions.BadJsonFileException;
import it.escape.core.server.model.game.exceptions.CellNotExistsException;
import it.escape.core.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.core.server.model.game.exceptions.MalformedStartingCells;
import it.escape.core.server.model.game.exceptions.PlayerCanNotEnterException;
import it.escape.core.server.model.game.gamemap.loader.MapLoader;
import it.escape.core.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.core.server.model.game.gamemap.positioning.CubicDeltas;
import it.escape.core.server.model.game.gamemap.positioning.Position2D;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.core.server.model.game.players.Player;
import it.escape.core.server.model.game.players.PlayerTeams;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.FilesHelper;
import it.escape.tools.utils.LogHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class GameMap implements MapActionInterface, MapPathfinderInterface {
	
	private static final Logger LOGGER = Logger.getLogger( GameMap.class.getName() );
	
	private Map<String, Cell> cells;		//this hashmap stores pairs such as: <A3,Cell(2,3,5)>
	
	private Map<Player, Cell> playersPositions;
	
	private Cell startAliens = null;
	
	private Cell startHumans = null;
	
	private String name;
	
	private Position2D maxSize;

	/** Constructor
	 * @throws MalformedStartingCells */
	public GameMap(String filename) throws BadJsonFileException, IOException, MalformedStartingCells {
		LogHelper.setDefaultOptions(LOGGER);
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
	
	public List<Cell> getNeighborCells(PositionCubic center) throws CellNotExistsException {
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
	
	public List<PositionCubic> getNeighborPositions(PositionCubic center) throws CellNotExistsException {
		List<Cell> vicini = getNeighborCells(center);
		List<PositionCubic> ret = new ArrayList<PositionCubic>();
		for (Cell c : vicini) {
			ret.add(c.getPosition());
		}
		return ret;
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
	public CellAction move(PlayerActionInterface curPlayer , String destination) throws BadCoordinatesException, DestinationUnreachableException, CellNotExistsException, PlayerCanNotEnterException {
		if (destination.equals(StringRes.getString("model.move.randomCoordinate"))) {
			PositionCubic dest3D = getPlayerPosition(curPlayer);
			return moveRandomly(curPlayer, dest3D);
		}
		else {
			PositionCubic dest3D = CoordinatesConverter.fromAlphaNumToCubic(destination);
			return move(curPlayer, dest3D);
		}
		
	}
	
	public CellAction moveRandomly(PlayerActionInterface curPlayer, PositionCubic start) throws CellNotExistsException {
		boolean done = false;
		CellAction action = null;
		Random rand = new Random();
		List<PositionCubic> possibilities = getNeighborPositions(start);
		while (!done) {
			PositionCubic destination = possibilities.get(rand.nextInt(possibilities.size()));
			LOGGER.finer("Moving to random position: " + destination.toString());
			try {
				action = move(curPlayer, destination);
				done = true;
			} catch (CellNotExistsException e) {
				done = false;
			} catch (PlayerCanNotEnterException e) {
				done = false;
			} catch (DestinationUnreachableException e) {
				done = false;
			}
		}
		return action;
	}
	
	private CellAction move(PlayerActionInterface curPlayer , PositionCubic dest3D) throws CellNotExistsException, PlayerCanNotEnterException, DestinationUnreachableException {
		if (!dest3D.equals(getPlayerPosition((Player)curPlayer))) {
			// if the destination is the cell we're in, we can bypass all this
			if (!cellExists(dest3D)) {
				throw new CellNotExistsException(StringRes.getString("messaging.exceptions.cellNotExists"));
			}
			Cell c = getCell(dest3D);
			if (!c.canEnter((Player)curPlayer)) {
				throw new PlayerCanNotEnterException(StringRes.getString("messaging.exceptions.playerCanNotEnter"));
			}
			if (!destinationReachable((Player)curPlayer, dest3D)) {
				throw new DestinationUnreachableException(StringRes.getString("messaging.exceptions.destinationUnreachable"));
			}
		} else {
			throw new DestinationUnreachableException();
		}
			
		updatePlayerPosition(curPlayer, dest3D);
		
		return getCell(dest3D).getCellAction(); 
	}
	
	public void updatePlayerPosition(PlayerActionInterface curPlayer, PositionCubic dest) {
		playersPositions.remove(curPlayer);
		playersPositions.put((Player)curPlayer,getCell(dest));
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
	
	@Override
	public Cell getPlayerCell(Player player) {
		return playersPositions.get(player);
	}
	
	@Override
	public PositionCubic getPlayerPosition(PlayerActionInterface player) {
		return playersPositions.get((Player)player).getPosition();
	}
	@Override
	public String getPlayerAlphaNumPosition(PlayerActionInterface player) {
		return CoordinatesConverter.fromCubicToAlphaNum(getPlayerPosition((Player)player));
	}
	
	/**
	 * invoked by GameMaster, add a player (human or alien)
	 * to the game, setting its position to the respective starting cell
	 * @param player
	 * @param team*/
	@Override
	public void addNewPlayer(PlayerActionInterface player, PlayerTeams team) {
		Cell startIn = null;
		if (team == PlayerTeams.HUMANS) {
			startIn = startHumans;
		} else {
			startIn = startAliens;
		}
		playersPositions.put((Player)player, startIn);
	}
	
	/**
	 * Returns a list of all player standing in a certain position.
	 * It is assumed that a cell does exist in said position.
	 * @param pos
	 * @return */
	@Override
	public List<PlayerActionInterface> getPlayersByPosition(PositionCubic pos) {
		List<PlayerActionInterface> ret = new ArrayList<PlayerActionInterface>();
		Iterator<Map.Entry<Player,Cell>> mapIterator = playersPositions.entrySet().iterator();
		while (mapIterator.hasNext()) {
			Map.Entry pair = mapIterator.next();
			Cell candidateCell = (Cell)pair.getValue();
			Player candidatePlayer = (Player)pair.getKey();
			if (pos.equals(candidateCell.getPosition())) {
				ret.add(candidatePlayer);
			}
		}
		return ret;
	}

	@Override
	public PositionCubic getStartHumans() {
		return startHumans.getPosition();
	}
	
	/**Given the position, returns a Cell)
	 * n: REQUIRES : cellExists returns true*/
	@Override
	public Cell getCell(PositionCubic pos3D) {
		return cells.get(CoordinatesConverter.fromCubicToAlphaNum(pos3D));
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
