package it.escape.server.model.game.actions;

import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.gamemap.GameMap;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class MoveCommand implements PlayerCommand {
	
	Position2D destination;
	
	public MoveCommand(Position2D destination) {
		this.destination = destination;
	}

	public void execute(Player currentPlayer) {
		GameMap.getMapInstance();
	}

}
