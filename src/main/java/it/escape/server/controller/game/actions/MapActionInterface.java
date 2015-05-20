package it.escape.server.controller.game.actions;

import java.util.List;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;

public interface MapActionInterface {

	public CellAction move(Player curPlayer , String destination) throws Exception;
	
	public void updatePlayerPosition(Player curPlayer, PositionCubic dest);
	
	public List<Player> getPlayersByPosition(PositionCubic pos);
	
	public PositionCubic getPlayerPosition(Player player);
	
	public void addNewPlayer(Player player, PlayerTeams team);
}
