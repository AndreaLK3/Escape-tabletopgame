package it.escape.server.controller;

import java.util.List;

import it.escape.server.model.game.actions.cellActions.NoCellAction;
import it.escape.server.model.game.actions.playerCommands.MoveCommand;
import it.escape.server.model.game.actions.playerCommands.PlayerCommand;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingInterface;

/** This class is located at the border of the controller package;
 * it communicates with the classes inside the View; 
 * depending on the strings returned by UserMessagesReporter,
 * it creates different actions.
 * whenever a certain kind of input is required by the TurnHandler.
 * @author andrea
 */
public class UserMessagesReporter {
		
	private static List<UserMessagesReporter> reportersList;
	
	private Player thePlayer;
	private MessagingInterface interfaceWithUser;
	
	public static UserMessagesReporter getReporterInstance(Player currentPlayer) {
		for (UserMessagesReporter r : reportersList)
		{	if (r.getThePlayer()==currentPlayer)
			return r;
		}
		return null;
	}
	
	private UserMessagesReporter(MessagingInterface interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}

	public static void createUMR(MessagingInterface interfaceWithUser) {
		reportersList.add(new UserMessagesReporter(interfaceWithUser));
	}
	
	/**This method assigns a player (already initialized) to an existing
	 * userMessagesReporter connected to an existing interface.
	 * @param newP
	 * @param interfaceWithUser2
	 */
	public static void bindPlayer(Player newP,MessagingInterface interfaceWithUser2) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getInterfaceWithUser() == interfaceWithUser2) {
				r.setThePlayer(newP);
			}
		}
	}
	
	/** This function, depending on the input given by the user, 
	 * returns a PlayerCommand object to the turn controller TurnHandler
	 * @param String : a message that the controller can send to the user
	 * @return PlayerCommand object, either playObjectCard or NoAction
	 */
	public boolean askIfObjectCard(String s) {
		//to be implemented
		return true;
	}

	
	public MoveCommand askForMovement() {
		Position2D destination;
		destination = askForPosition();
		return new MoveCommand(destination);
	}
	

	public Position2D askForNoise() {
		Position2D location;
		//to be implemented 
		location = askForPosition();
		return location;
	}
	
	public String getCardKey() {
		//to be implemented
		return new String ("adrenaline");
	}

	private Position2D askForPosition() {
		return new Position2D(2,3);
	}


	public Player getThePlayer() {
		return thePlayer;
	}


	public void setThePlayer(Player thePlayer) {
		this.thePlayer = thePlayer;
	}


	public MessagingInterface getInterfaceWithUser() {
		return interfaceWithUser;
	}


	public void setInterfaceWithUser(MessagingInterface interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}

	public static List<UserMessagesReporter> getReportersList() {
		return reportersList;
	}

	public static void setReportersList(List<UserMessagesReporter> reportersList) {
		UserMessagesReporter.reportersList = reportersList;
	}


	
}
