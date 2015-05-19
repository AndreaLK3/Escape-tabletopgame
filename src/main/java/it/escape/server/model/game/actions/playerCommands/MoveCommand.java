package it.escape.server.model.game.actions.playerCommands;

import it.escape.server.model.game.actions.cellActions.CellAction;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.players.Player;

public class MoveCommand implements PlayerCommand {
	
	Position2D destination;
	
	public MoveCommand(Position2D destination) {
		this.destination = destination;
	}

	public CellAction execute(Player currentPlayer) throws Exception {
		
		CellAction actionFromCell;
		
		actionFromCell = GameMap.getMapInstance().move(currentPlayer, destination);
		return actionFromCell;
	}

}
