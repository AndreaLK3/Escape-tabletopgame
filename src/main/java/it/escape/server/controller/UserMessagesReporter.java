package it.escape.server.controller;

import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingInterface;
import it.escape.strings.StringRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
 * This class is located at the border of the controller package,
 * it communicates with the classes inside the View;
 * This class statically links a player in the game to a MessagingInterface,
 * thus automatically routing any user-specific communications.
 * The TurnHandler issues requests to this class.
 * N: The check on the syntax is not performed by this class, it is done by the interface
 * The check on the semantics is performed by TurnHandler.
 * This class makes use of the Synchronous-communication facilities offered by MessagingInterface
 * @author andrea
 */
public class UserMessagesReporter {
		
	private static List<UserMessagesReporter> reportersList = new ArrayList<UserMessagesReporter>();
	
	private Player thePlayer;
	private MessagingInterface interfaceWithUser;
	
	private boolean overrideDefault = false;
	
	//creation and access methods
	
	public static UserMessagesReporter getReporterInstance(Player currentPlayer) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getThePlayer()==currentPlayer)
			return r;
		}
		return null;
	}
	
	public static UserMessagesReporter getReporterInstance(MessagingInterface interfaceWithUser) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getInterfaceWithUser()==interfaceWithUser)
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
	 * @param interfaceWithUser
	 */
	public static void bindPlayer(Player newP,MessagingInterface interfaceWithUser) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getInterfaceWithUser() == interfaceWithUser) {
				r.setThePlayer(newP);
				break;
			}
		}
	}
	
	public void fillinDefaultOnce() {
		interfaceWithUser.overrideDefaultOption();
	}
	
	/**
	 * if this is set, the message reporter will always choose
	 * the default option, without bothering to interact
	 * with the user
	 */
	public void fillinDefaultAlways() {
		overrideDefault = true;
	}
	
	public void stopFillingDefault() {
		if (overrideDefault) {
			overrideDefault = false;
		}
	}
	
	//methods to answer to TurnHandler's requests
	
	/** This function returns the user's answer to the question:
	 * do you wish to use any Object cards?
	 * @param String : a message that the controller can send to the user
	 * @return boolean
	 */
	public boolean askIfObjectCard(String s) {
		String defaultChoice = "no";
		if (overrideDefault) {
			return false;
		}
		else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askPlayObjectCard"));
			String ans = ioGetBinaryChoice(defaultChoice,"yes","no").toLowerCase();
			if (ans.equals("yes")) {
				return true;
			}
			return false;
		}
	}
	

	public boolean askIfAttack() {
		//to be implemented
		return false;
		
	}
	
	
	/**
	 * Ask the user (who has too much cards in his hand) if he wants to play
	 * or discard the extra card
	 * @return true for play, false for discard
	 */
	public boolean askPlayCardOrDiscard() {
		String defaultChoice = "discard";
		if (overrideDefault) {
			return false;
		}
		else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askPlayObjectCard"));
			String answer = ioGetBinaryChoice(defaultChoice,"play","discard").toLowerCase();
			if (answer.equals("yes")) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Queries the user for an object card to play / discard
	 * It is assumed that the user / client program already knows
	 * the cards in her possession.
	 * @return string representing the chosen card
	 */
	public String askWhichObjectCard() {
		String defaultChoice = "adrenaline";
		/* To be implemented
		 * returns a validated string from the Client
		 */
		return "adrenaline";
	}
	
	/**
	 * Queries the user for a position to move to
	 * @return
	 */
	public MoveCommand askForMovement() {
		String destination;
		destination = ioGetPosition();
		return new MoveCommand(destination);
	}
	

	public String askForNoisePosition() {
		String location;
		//to be implemented 
		location = ioGetPosition();
		return location;
	}
	
	public String askForLightsPosition() {
		String location;
		//to be implemented 
		location = ioGetPosition();
		return location;
	}
	
	public String ioGetCardKey() {
		//to be implemented
		return new String ("adrenaline");
	}

	private String ioGetPosition() {
		interfaceWithUser.writeToClient(String.format(
				StringRes.getString("messaging.askForPosition")));
		// TODO: we should set the player's position as the default option
		interfaceWithUser.setDefaultOption("");
		interfaceWithUser.setContext(null);
		return interfaceWithUser.readFromClient();
	}
	
	private String ioGetBinaryChoice(String defaultOption, String yes, String no) {
		interfaceWithUser.writeToClient(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				yes,
				no));
		interfaceWithUser.setDefaultOption(defaultOption);
		interfaceWithUser.setContext(Arrays.asList(yes,no));
		return interfaceWithUser.readFromClient();
	}
	
	public void relayMessage(String string) {
		interfaceWithUser.writeToClient(string);
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


