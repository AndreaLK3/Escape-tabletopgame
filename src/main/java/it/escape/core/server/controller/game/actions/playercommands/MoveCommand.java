package it.escape.core.server.controller.game.actions.playercommands;

import it.escape.core.server.controller.game.actions.CellAction;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.exceptions.BadCoordinatesException;
import it.escape.core.server.model.game.exceptions.CellNotExistsException;
import it.escape.core.server.model.game.exceptions.DestinationUnreachableException;
import it.escape.core.server.model.game.exceptions.PlayerCanNotEnterException;

public class MoveCommand {
	
	String destination;
	
	public MoveCommand(String destination) {
		this.destination = destination;
	}

	public CellAction execute(PlayerActionInterface currentPlayer, MapActionInterface map) throws BadCoordinatesException, DestinationUnreachableException, CellNotExistsException, PlayerCanNotEnterException {
		
		CellAction actionFromCell;
		
		actionFromCell = map.move(currentPlayer, destination);
		
		return actionFromCell;
	}
}
