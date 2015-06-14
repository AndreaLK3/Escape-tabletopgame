package it.escape.server.controller;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannelStrings;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/** 
 * This class is located at the border of the controller package,
 * it communicates with the classes inside the View;
 * This class statically links a player in the game to a MessagingChannelStrings,
 * thus automatically routing any user-specific communications.
 * The TurnHandler issues requests to this class.
 * N: The check on the syntax is not performed by this class, it is done by the interface
 * The check on the semantics is performed by TurnHandler.
 * This class makes use of the Synchronous-communication facilities offered by MessagingChannelStrings
 * @author andrea
 */
public class UserMessagesReporterSocket extends UserMessagesReporter {
		
	private MessagingChannelStrings interfaceWithUser;
	
	private boolean automaticOverriding = false;
	
	private Announcer announcerRef;
	
	//creation methods
	
	public UserMessagesReporterSocket(MessagingChannelStrings interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}
	
	@Override
	public void bindAnnouncer(Announcer announcer) {
		this.announcerRef = announcer;
	}
	
	@Override
	public void fillinDefaultOnce() {
		log.finer("Overriding default once...");
		interfaceWithUser.overrideDefaultOption();
	}
	
	/**
	 * if this is set, the message reporter will always choose
	 * the default option, without bothering to interact
	 * with the user
	 */
	@Override
	public void fillinDefaultAlways() {
		automaticOverriding = true;
	}
	
	@Override
	public void stopFillingDefault() {
		if (automaticOverriding) {
			automaticOverriding = false;
		}
	}
	/**
	 * Frequently used code
	 * @param position
	 */
	@Override
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
	@Override
	public boolean askIfObjectCard() {
		String message = StringRes.getString("messaging.askPlayObjectCard");
		return askForYesNoChoice(message);
	}
	

	@Override
	public boolean askIfAttack() {
		String message = StringRes.getString("messaging.askIfAttack");
		return askForYesNoChoice(message);
	}
	
	private boolean askForYesNoChoice(String question) {
		String defaultChoice = "no";
		if (automaticOverriding) {
			log.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.writeToClient(question);
			String ans = ioGetBinaryChoice(defaultChoice,"yes","no").toLowerCase();
			if (ans.equals("yes")) {
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
	@Override
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
	@Override
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
	@Override
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
	

	@Override
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
	
	@Override
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
	
	private String ioGetCardKey() {
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
	@Override
	public void relayMessage(String string) {
		interfaceWithUser.writeToClient(string);
	}
	
	

	@Override
	public MessagingChannelStrings getInterfaceWithUser() {
		return interfaceWithUser;
	}


	@Override
	public Announcer getAnnouncer() {
		return announcerRef;
	}
	
}


