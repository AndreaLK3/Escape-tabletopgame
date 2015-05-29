package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class, with the purpose of calculating whether a
 * player can reach a specified cell.
 * It employs a breadth-first search algorithm.
 * The algorithm won't attempt to cross any cell forbidden
 * to the player (i.e. canEnter() == false)
 * @author michele
 *
 */
public class PathFinder {
	
	private MapPathfinderInterface map;
	
	private Player curPlayer;
	
	private PositionCubic dest;
	
	private List<List> fringes;

	public PathFinder(MapPathfinderInterface map, Player curPlayer, PositionCubic dest) {
		this.map = map;
		this.curPlayer = curPlayer;
		this.dest = dest;
		fringes = new ArrayList<List>();
	}

	public void calculateRoute() throws CellNotExistsException {
		// algoritmo di raggiunngibilità (ricerca breadth first sulle celle)
		List<Cell> visited = new ArrayList<Cell>();
		Cell start = map.getPlayerCell(curPlayer);
		visited.add(start);
		fringes.add( new ArrayList<Cell>() );
		fringes.get(0).add(start);
		
		for (int i = 1; i <= curPlayer.getMaxRange(); i++) {
			fringes.add( (new ArrayList<Cell>()) );
			List<Cell> previous = fringes.get(i - 1);
			List<Cell> current = fringes.get(i);
			for (Cell cube : previous) {
				List<Cell> neighbors = map.getNeighbors(cube.getPosition());
				for (Cell neighbor : neighbors) {
					if (!visited.contains(neighbor))
						if(neighbor.canEnter(curPlayer)) {
							visited.add(neighbor);
							current.add(neighbor);
					}
				}
				
			}
		}
	}
	
	public boolean isReachable() {
		for (int i = 0; i <= curPlayer.getMaxRange(); i++) {
			if (fringes.get(i).contains(map.getCell(dest))) {
				return true;
		}
		}
		return false;
	}
}
