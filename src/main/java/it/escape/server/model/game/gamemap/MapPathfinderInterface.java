package it.escape.server.model.game.gamemap;

import java.util.List;

import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

public interface MapPathfinderInterface {
	
	public Cell getPlayerCell(Player player);
	
	public List<Cell> getNeighbors(PositionCubic center) throws CellNotExistsException;
	
	public Cell getCell(PositionCubic pos3D);
}
