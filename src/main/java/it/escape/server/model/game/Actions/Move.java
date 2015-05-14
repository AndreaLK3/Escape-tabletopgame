package it.escape.server.model.game.Actions;

import it.escape.server.controller.Player;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class Move implements PlayerAction {
	
	Position2D destination;
	
	public Move(Position2D destination) {
		this.destination = destination;
	}

	public void execute(Player currentPlayer) {
		currentPlayer.getTheMap().moveCharacter(currentPlayer.getMyCharacter(), destination);
		
	}

}
