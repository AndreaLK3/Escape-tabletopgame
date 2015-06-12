package it.escape.server.controller;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannel;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/** 
 * This class is located at the border of the controller package,
 * it communicates with the classes inside the View;
 * This class statically links a player in the game to a MessagingChannel,
 * thus automatically routing any user-specific communications.
 * The TurnHandler issues requests to this class.
 * N: The check on the syntax is not performed by this class, it is done by the interface
 * The check on the semantics is performed by TurnHandler.
 * This class makes use of the Synchronous-communication facilities offered by MessagingChannel
 * @author andrea
 */
public class UserMessagesReporter {
	
	protected static final Logger log = Logger.getLogger( UserMessagesReporter.class.getName() );
		
	private static List<UserMessagesReporter> reportersList = new ArrayList<UserMessagesReporter>();
	
	private Player thePlayer;
	private MessagingChannel interfaceWithUser;
	
	private boolean automaticOverriding = false;
	
	private Announcer announcerRef;
	
	//creation and access methods
	
	public static UserMessagesReporter getReporterInstance(PlayerActionInterface currentPlayer) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getThePlayer() == (Player)currentPlayer)
			return r;
		}
		return null;
	}
	
	public static UserMessagesReporter getReporterInstance(MessagingChannelInterface interfaceWithUser) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getInterfaceWithUser() == (MessagingChannel)interfaceWithUser)
			return r;
		}
		return null;
	}
	
	private UserMessagesReporter(MessagingChannel interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}

	public static void createUMR(MessagingChannel interfaceWithUser) {
		if (reportersList.size() <= 0) {
			LogHelper.setDefaultOptions(log);
		}
		reportersList.add(new UserMessagesReporter(interfaceWithUser));
	}
	
	/**This method assigns a player (already initialized) to an existing
	 * userMessagesReporter connected to an existing interface.
	 * @param newP
	 * @param interfaceWithUser
	 */
	public static void bindPlayer(Player newP,MessagingChannel interfaceWithUser) {
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
		log.finer("Overriding default once...");
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
	/**
	 * Frequently used code
	 * @param position
	 */
	public void reportMyUserPosition(String position) {
		relayMessage(String.format(
				StringRes.getString("messaging.hereYouAre"),
				position));
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
			log.finer("automaticOverriding: return false");
			return false;
		} else {
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
			log.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askIfAttack"));
			String answer = ioGetBinaryChoice(defaultChoice,"yes","no").toLowerCase();
			if (answer.equals("yes")) {
				return true;
			}
			return false;
		}
	}
	
	
	/**
	 * Ask the user (who has too many cards in his hand) if he wants to play
	 * or discard the extra card
	 * @return true for play, false for discard
	 */
	public boolean askPlayCardOrDiscard() {
		String defaultChoice = "discard";
		if (automaticOverriding) {
			log.finer("automaticOverriding: return false");
			return false;
		} else {
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
	 * @return string representing the chosen card	 */
	public String askWhichObjectCard(String defaultCard) {
		log.finer("Server is asking which object card the user intends to play/discard");
		String defaultChoice = defaultCard;
		
		if (automaticOverriding) {
			log.finer("automaticOverriding: return " + defaultChoice);
			return defaultChoice;		
		} else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askWhichObjectCard"));
			String cardNames[] = thePlayer.getMyHand().getAllCardNames();
			return ioGetCardKey();
		}
	}
	
	/**
	 * Queries the user for a position to move to
	 * @param playerCurrentPos 
	 * @return
	 */
	public MoveCommand askForMovement(String playerCurrentPos) {
		if (automaticOverriding) {
			log.finer("automaticOverriding: return MoveCommand(" + playerCurrentPos + ")");
			return new MoveCommand(playerCurrentPos);		
		}
		interfaceWithUser.writeToClient(StringRes.getString("messaging.timeToMove"));
		String destination;
		destination = ioGetPosition(playerCurrentPos);
		return new MoveCommand(destination);
	}
	

	public String askForNoisePosition(String playerCurrentPos) {
		if (automaticOverriding) {
			log.finer("automaticOverriding: return " + playerCurrentPos);
			return playerCurrentPos;		
		}
		String location;
		interfaceWithUser.writeToClient(StringRes.getString("messaging.askForNoisePosition"));
		location = ioGetPosition(playerCurrentPos);
		return location;
	}
	
	public String askForLightsPosition(String playerCurrentPos) {
		if (automaticOverriding) {
			log.finer("automaticOverriding: return " + playerCurrentPos);
			return playerCurrentPos;		
		}
		String location;
		interfaceWithUser.writeToClient(StringRes.getString("messaging.askForLightsPosition"));
		location = ioGetPosition(playerCurrentPos);
		return location;
	}
	
	public String ioGetCardKey() {
		interfaceWithUser.writeToClient(StringRes.getString("messaging.ownedCards"));
		interfaceWithUser.writeToClient(thePlayer.getMyHand().getAllCardNamesAsString());
		interfaceWithUser.setDefaultOption("none");
		interfaceWithUser.setContext(Arrays.asList("attack","defense","teleport", "lights", "sedatives", "adrenaline"));
		return interfaceWithUser.readFromClient();
	}

	private String ioGetPosition(String playerCurrentPos) {
		interfaceWithUser.writeToClient(String.format(
				StringRes.getString("messaging.askForPosition")));
		interfaceWithUser.setDefaultOption(playerCurrentPos);
		interfaceWithUser.setContext(Arrays.asList("([A-Z]+)([0-9]+)"));  // regular expression
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
	
	
	/**This method is used when we just need to send a message to the user.
	 * We have no special questions to ask.
	 * @param string */
	public void relayMessage(String string) {
		interfaceWithUser.writeToClient(string);
	}
	
	/* E' utile? TurnHandler ha già un riferimento al Player*/
	public Player getThePlayer() {
		return thePlayer;
	}

	public MessagingChannel getInterfaceWithUser() {
		return interfaceWithUser;
	}

	private void setThePlayer(Player thePlayer) {
		this.thePlayer = thePlayer;
	}

	public Announcer getAnnouncer() {
		return announcerRef;
	}
	
}


