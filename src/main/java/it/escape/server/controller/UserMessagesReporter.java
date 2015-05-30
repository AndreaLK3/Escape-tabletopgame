package it.escape.server.controller;

import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.Announcer;
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
	
	private boolean automaticOverriding = false;
	
	private Announcer announcerRef;
	
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
	
	public void bindAnnouncer(Announcer announcer) {
		this.announcerRef = announcer;
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
		automaticOverriding = true;
	}
	
	public void stopFillingDefault() {
		if (automaticOverriding) {
			automaticOverriding = false;
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
		if (automaticOverriding) {
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
		String defaultChoice = "no";
		if (automaticOverriding) {
			return false;
		}
		else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askIfAttack"));
			String answer = ioGetBinaryChoice(defaultChoice,"yes","no").toLowerCase();
			if (answer.equals("yes")) {
				return true;
			}
			else
				return false;
		}
	}
	
	
	/**
	 * Ask the user (who has too much cards in his hand) if he wants to play
	 * or discard the extra card
	 * @return true for play, false for discard
	 */
	public boolean askPlayCardOrDiscard() {
		String defaultChoice = "discard";
		if (automaticOverriding) {
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
		String defaultChoice = "none";
		
		if (automaticOverriding) {
			return defaultChoice;		
		}
		else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askWhichObjectCard"));
			return ioGetCardKey();
		}
	}
	
	/**
	 * Queries the user for a position to move to
	 * @param playerCurrentPos 
	 * @return
	 */
	public MoveCommand askForMovement(String playerCurrentPos) {
		interfaceWithUser.writeToClient(StringRes.getString("messaging.timeToMove"));
		String destination;
		destination = ioGetPosition(playerCurrentPos);
		return new MoveCommand(destination);
	}
	

	public String askForNoisePosition(String playerCurrentPos) {
		String location;
		interfaceWithUser.writeToClient(StringRes.getString("messaging.askForNoisePosition"));
		location = ioGetPosition(playerCurrentPos);
		return location;
	}
	
	public String askForLightsPosition(String playerCurrentPos) {
		String location;
		interfaceWithUser.writeToClient(StringRes.getString("messaging.askForLightsPosition"));
		location = ioGetPosition(playerCurrentPos);
		return location;
	}
	
	public String ioGetCardKey() {
		interfaceWithUser.setDefaultOption("none");
		interfaceWithUser.setContext(Arrays.asList("attack", "teleport", "lights", "sedatives", "adrenaline"));
		return interfaceWithUser.readFromClient();
	}

	private String ioGetPosition(String playerCurrentPos) {
		interfaceWithUser.writeToClient(String.format(
				StringRes.getString("messaging.askForPosition")));
		interfaceWithUser.setDefaultOption("playerCurrentPos");
		interfaceWithUser.setContext(null);
		return interfaceWithUser.readFromClient();
	}
	
	private String ioGetBinaryChoice(String defaultOption, String first, String second) {
		interfaceWithUser.writeToClient(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				first,
				second));
		interfaceWithUser.setDefaultOption(defaultOption);
		interfaceWithUser.setContext(Arrays.asList(first,second));
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

	public Announcer getAnnouncer() {
		return announcerRef;
	}
	
}


