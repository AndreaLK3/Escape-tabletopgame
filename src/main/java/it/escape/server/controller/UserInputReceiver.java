package it.escape.server.controller;

import it.escape.server.model.game.Actions.Move;
import it.escape.server.model.game.Actions.PlayerAction;
import it.escape.server.model.game.gamemap.positioning.Position2D;

public class UserInputReceiver {
	
	Position2D destination;
	
	public PlayerAction getMovement() {
		return new Move(destination);
	}

}
