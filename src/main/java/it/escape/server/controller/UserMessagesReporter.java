package it.escape.server.controller;

import it.escape.server.model.game.actions.cellActions.NoCellAction;
import it.escape.server.model.game.actions.playerCommands.MoveCommand;
import it.escape.server.model.game.actions.playerCommands.PlayerCommand;
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
	public boolean askIfObjectCard(String s) {
		//to be implemented
		return false;
	}

	
	public MoveCommand askForMovement(String string) {
		Position2D destination;
		destination = askForPosition();
		return new MoveCommand(destination);
	}
	

	public Position2D askForNoise(String string) {
		Position2D location;
		//to be implemented
		location = askForPosition();
		return location;
	}

	private Position2D askForPosition() {
		return new Position2D(2,3);
	}
}
