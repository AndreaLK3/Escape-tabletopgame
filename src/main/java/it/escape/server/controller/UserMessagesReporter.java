package it.escape.server.controller;

import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingInterface;

import java.util.List;

/** This class is located at the border of the controller package, it communicates with the classes inside the View; 
 * The TurnHandler issues requests to this class;
 * depending on the strings returned by MessagingInterface,
 * this class creates different actions, and returns them to the TurnHandler.
 * N: The check on the syntax is not performed by this class, it is done by the interface.
 * The check on the semantics is performed by TurnHandler.
 * @author andrea
 */
public class UserMessagesReporter {
		
	private static List<UserMessagesReporter> reportersList;
	
	private Player thePlayer;
	private MessagingInterface interfaceWithUser;
	
	//creation and access methods
	
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
	
	//methods to answer to TurnHandler's requests
	
	/** This function returns the user's answer to the question:
	 * do you wish to use any Object cards?
	 * @param String : a message that the controller can send to the user
	 * @return boolean
	 */
	public boolean askIfObjectCard(String s) {
		/*if string returned == yes
		 * return true
		 * else 
		 * return false
		 */
		return true;
	}

	public String askWhichObjectCard() {
		/* To be implemented
		 * returns a validated string from the Client
		 */
		return "Adrenaline";
	}
	
	public MoveCommand askForMovement() {
		String destination;
		destination = askForPosition();
		return new MoveCommand(destination);
	}
	

	public String askForNoisePosition() {
		String location;
		//to be implemented 
		location = askForPosition();
		return location;
	}
	
	public String getCardKey() {
		//to be implemented
		return new String ("adrenaline");
	}

	private String askForPosition() {
		// to be implemented
		return "B03";
	}

	
	/* E' utile? TurnHandler ha già un riferimento al Player*/
	public Player getThePlayer() {
		return thePlayer;
	}

	public MessagingInterface getInterfaceWithUser() {
		return interfaceWithUser;
	}

	private void setThePlayer(Player thePlayer) {
		this.thePlayer = thePlayer;
	}
	
}


