package it.escape.server.model.game.actions.playerCommands;

import it.escape.server.model.game.actions.cellActions.CellAction;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.players.Player;

public class MoveCommand implements PlayerCommand {
	
	String destination;
	
	public MoveCommand(String destination) {
		this.destination = destination;
	}

	public CellAction execute(Player currentPlayer) throws Exception {
		
		CellAction actionFromCell;
		
		actionFromCell = GameMap.getMapInstance().move(currentPlayer, destination);
		return actionFromCell;
	}

}
