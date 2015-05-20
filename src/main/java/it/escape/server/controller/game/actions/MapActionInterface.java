package it.escape.server.controller.game.actions;

import java.util.List;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

public interface MapActionInterface {

	public CellAction move(Player curPlayer , String destination) throws Exception;
	
	public List<Player> getPlayersByPosition(PositionCubic pos);
	
	public PositionCubic getPlayerPosition(Player player);
}
