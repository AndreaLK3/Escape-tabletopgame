package it.escape.server.controller.game.actions.playercommands;

import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerCommand;
import it.escape.server.model.game.players.Player;

public class MoveCommand implements PlayerCommand {
	
	String destination;
	
	public MoveCommand(String destination) {
		this.destination = destination;
	}

	public CellAction execute(Player currentPlayer, MapActionInterface map) throws Exception {
		
		CellAction actionFromCell;
		
		actionFromCell = map.move(currentPlayer, destination);
		
		return actionFromCell;
	}
}
