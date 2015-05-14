package it.escape.server.model.game.Actions;

import it.escape.server.controller.Player;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class Move implements PlayerAction {
	
	PositionCubic destination;
	
	public Move(Position2D destination) {
		this.destination = CoordinatesConverter.fromOddqToCubic(destination);
	}

	public void execute(Player currentPlayer) {
		try {
		currentPlayer.getTheMap().moveCharacter(currentPlayer.getMyCharacter(), destination);
		}
		catch (Exception e) {
			
		}
		
	}

}
