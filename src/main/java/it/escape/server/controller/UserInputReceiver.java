package it.escape.server.controller;

import it.escape.server.model.game.Actions.Move;
import it.escape.server.model.game.Actions.PlayerAction;
import it.escape.server.model.game.gamemap.positioning.Position2D;

public class UserInputReceiver {
	
	Position2D destination;
	
	public PlayerAction getInitialPlayerAction () {
		/*if command == Move
		 * 		askforDestination
		 * if command == playObjectCard
		 * 
		 */
		return new Move(destination);
	}

}
