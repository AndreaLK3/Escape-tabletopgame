package it.escape.server.controller;

import it.escape.server.model.game.actions.cellActions.NoCellAction;
import it.escape.server.model.game.actions.playerCommands.MoveCommand;
import it.escape.server.model.game.actions.playerCommands.PlayerCommand;

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
		String destination;
		destination = askForPosition();
		return new MoveCommand(destination);
	}
	

	public String askForNoise(String string) {
		String location;
		//to be implemented 
		location = askForPosition();
		return location;
	}

	private String askForPosition() {
		return new String("B03");
	}
}
