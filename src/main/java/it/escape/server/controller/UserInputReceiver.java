package it.escape.server.controller;

import it.escape.server.model.game.actions.MoveCommand;
import it.escape.server.model.game.actions.PlayerCommand;
import it.escape.server.model.game.gamemap.positioning.Position2D;

public class UserInputReceiver {
	
	Position2D destination;
	
	public PlayerCommand getMovement() {
		return new MoveCommand(destination);
	}

}
