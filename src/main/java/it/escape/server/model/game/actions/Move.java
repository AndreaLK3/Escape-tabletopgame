package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class Move implements PlayerAction {
	
	PositionCubic destination;
	
	public Move(Position2D destination) {
		this.destination = CoordinatesConverter.fromOddqToCubic(destination);
	}

	public void execute(Player currentPlayer) {
		
	}

}
