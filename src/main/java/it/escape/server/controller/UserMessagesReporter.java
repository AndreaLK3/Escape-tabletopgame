package it.escape.server.controller;

import it.escape.server.model.game.actions.MoveCommand;
import it.escape.server.model.game.actions.NoAction;
import it.escape.server.model.game.actions.PlayerCommand;
import it.escape.server.model.game.gamemap.positioning.Position2D;

/** This class is located at the border of the controller package;
 * it communicates with the classes inside the View, to ask the user for input,
 * whenever a certain kind of input is required by the TurnHandler.
 * It is a Singleton.
 * @author andrea
 */
public class UserMessagesReporter {
	
	public static UserMessagesReporter reporter;
	
	private UserMessagesReporter() {
		
	}
	
	public static UserMessagesReporter getReporterInstance () {
		if (reporter==null)
			reporter = new UserMessagesReporter();
		return reporter;
	}
	
	
	/** This function, depending on the input given by the user, 
	 * returns a PlayerCommand object to the turn controller TurnHandler
	 * @param String : a message that the controller can send to the user
	 * @return PlayerCommand object, either playObjectCard or NoAction
	 */
	public PlayerCommand askForObjectCard(String s) {
		//to be implemented
		return new NoAction();
	}

	public PlayerCommand askForPosition(String string) {
		Position2D destination;
		//to be implemented
		destination = new Position2D(2,3);
		return new MoveCommand(destination);
	}

}
