package it.escape.server.controller;

import it.escape.server.model.game.actions.Move;
import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.gamemap.positioning.Position2D;

public class UserInputReceiver {
	
	Position2D destination;
	
	public PlayerAction getMovement() {
		return new Move(destination);
	}

}
