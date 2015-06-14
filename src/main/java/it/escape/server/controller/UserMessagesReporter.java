package it.escape.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannelInterface;
import it.escape.server.view.MessagingChannelStrings;
import it.escape.server.view.MessagingChannelRMI;
import it.escape.utils.LogHelper;

/**
 * Abstract user messages reporter, it holds the static logic
 * which enroutes player<-->messagingChannel.
 * This class will instantiate new UserMessagesReporter object,
 * automatically from either the socket or the rmi subclasses.
 * From that moment on, the rest of the program does not
 * need to know which connection type we are using, and will
 * just use methods from UserMessagesReporter or MessagingChannelInterface
 * @author michele
 *
 */
public abstract class UserMessagesReporter {

	protected static final Logger log = Logger.getLogger( UserMessagesReporter.class.getName() );
	
	protected static List<UserMessagesReporter> reportersList = new ArrayList<UserMessagesReporter>();
	
	protected  Player thePlayer;
	
	protected boolean automaticOverriding = false;
	
	/**General creation/access method n.1*/
	public static UserMessagesReporter getReporterInstance(PlayerActionInterface currentPlayer) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getThePlayer() == (Player)currentPlayer)
			return r;
		}
		return null;
	}
	/**General creation/access method n.2*/
	public static UserMessagesReporter getReporterInstance(MessagingChannelInterface interfaceWithUser) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getInterfaceWithUser() == interfaceWithUser)
			return r;
		}
		return null;
	}
	/** Create a new UMR, dynamically deciding if it will be Socket or RMI */
	public static void createUMR(MessagingChannelInterface interfaceWithUser) {
		if (reportersList.size() <= 0) {
			LogHelper.setDefaultOptions(log);
		}
		if (interfaceWithUser instanceof MessagingChannelRMI) {
			reportersList.add(new UserMessagesReporterRMI((MessagingChannelRMI) interfaceWithUser));
		} else if (interfaceWithUser instanceof MessagingChannelStrings) {
			reportersList.add(new UserMessagesReporterSocket((MessagingChannelStrings) interfaceWithUser));
		}
		
	}
	
	/**This method assigns a player (already initialized) to an existing
	 * userMessagesReporter connected to an existing interface.
	 * @param newP
	 * @param interfaceWithUser
	 */
	public static void bindPlayer(Player newP,MessagingChannelInterface interfaceWithUser) {
		for (UserMessagesReporter r : reportersList) {	
			if (r.getInterfaceWithUser() == interfaceWithUser) {
				r.setThePlayer(newP);
				break;
			}
		}
	}
	
	/**Setter and getter methods for the Player*/
	protected void setThePlayer(Player thePlayer) {
		this.thePlayer = thePlayer;
	}
	
	public Player getThePlayer() {
		return thePlayer;
	}
	
	public abstract void bindAnnouncer(Announcer announcer);

	public abstract void fillinDefaultOnce();

	/**
	 * if this is set, the message reporter will always choose
	 * the default option, without bothering to interact
	 * with the user
	 */
	public abstract void fillinDefaultAlways();

	public abstract void stopFillingDefault();

	/**
	 * Frequently used code
	 * @param position
	 */
	public abstract void reportMyUserPosition(String position);

	/** This function returns the user's answer to the question:
	 * do you wish to use any Object cards?
	 * @param String : a message that the controller can send to the user
	 * @return boolean
	 */
	public abstract boolean askIfObjectCard();

	public abstract boolean askIfAttack();

	/**
	 * Ask the user (who has too many cards in his hand) if he wants to play
	 * or discard the extra card
	 * @return true for play, false for discard
	 */
	public abstract boolean askPlayCardOrDiscard();

	/**
	 * Queries the user for an object card to play / discard
	 * It is assumed that the user / client program already knows
	 * the cards in her possession.
	 * @return string representing the chosen card	 */
	public abstract String askWhichObjectCard(String defaultCard);

	/**
	 * Queries the user for a position to move to
	 * @param playerCurrentPos 
	 * @return
	 */
	public abstract MoveCommand askForMovement(String playerCurrentPos);

	public abstract String askForNoisePosition(String playerCurrentPos);

	public abstract String askForLightsPosition(String playerCurrentPos);

	/**This method is used when we just need to send a message to the user.
	 * We have no special questions to ask.
	 * @param string */
	public abstract void relayMessage(String string);

	public abstract MessagingChannelInterface getInterfaceWithUser();

	public abstract Announcer getAnnouncer();

}