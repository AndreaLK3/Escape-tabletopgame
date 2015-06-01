package it.escape.server.controller.game.actions;

import java.util.List;

import it.escape.server.model.game.exceptions.CellNotExistsException;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.PlayerTeams;

public interface MapActionInterface {

	public CellAction move(PlayerActionInterface curPlayer , String destination) throws Exception;
	
	public void updatePlayerPosition(PlayerActionInterface curPlayer, PositionCubic dest);
	
	public List<PlayerActionInterface> getPlayersByPosition(PositionCubic pos);
	
	public PositionCubic getPlayerPosition(PlayerActionInterface player);
	
	public String getPlayerAlphaNumPosition(PlayerActionInterface player);
	
	public void addNewPlayer(PlayerActionInterface player, PlayerTeams team);
	
	public PositionCubic getStartHumans();

	public boolean cellExists(String posAlphaNum);

	public List<PositionCubic> getNeighborPositions(PositionCubic center) throws CellNotExistsException;
	
	public String getName();
}
