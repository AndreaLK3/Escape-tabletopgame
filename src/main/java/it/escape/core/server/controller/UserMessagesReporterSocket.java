package it.escape.core.server.controller;

import it.escape.core.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.core.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.core.server.view.MessagingChannelInterface;
import it.escape.core.server.view.MessagingChannelStrings;
import it.escape.tools.strings.StringRes;

import java.util.Arrays;

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
	
	//creation methods
	
	public UserMessagesReporterSocket(MessagingChannelStrings interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}
	
	@Override
	public MessagingChannelInterface getInterfaceWithUser() {
		return interfaceWithUser;
	}
	
	@Override
	public void fillinDefaultOnce() {
		LOGGER.finer("Overriding default once...");
		interfaceWithUser.overrideDefaultOption();
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
			LOGGER.finer("automaticOverriding: return false");
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
		relayMessage(StringRes.getString("messaging.tooManyCardsHuman"));
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return false");
			return false;
		} else {
			String answer = ioGetBinaryChoice(defaultChoice,"play","discard").toLowerCase();
			if (answer.equals("play")) {
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
		LOGGER.finer("ServerSocketCore is asking which object card the user intends to play/discard");
		String defaultChoice = defaultCard;
		
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return " + defaultChoice);
			return defaultChoice;		
		} else {
			interfaceWithUser.writeToClient(StringRes.getString("messaging.askWhichObjectCard"));
			return ioGetCardKey();
		}
	}
	
	public void reportHaveToDiscard() {
		relayMessage(StringRes.getString("messaging.tooManyCardsAlien"));
	}
	
	/**
	 * Queries the user for a position to move to
	 * @param playerCurrentPos 
	 * @return
	 */
	@Override
	public MoveCommand askForMovement(String playerCurrentPos) {
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return MoveCommand(" + playerCurrentPos + ")");
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
			LOGGER.finer("automaticOverriding: return " + playerCurrentPos);
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
			LOGGER.finer("automaticOverriding: return " + playerCurrentPos);
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
		interfaceWithUser.setContext(Arrays.asList("attack","defense","teleport", "lights", "sedatives", "adrenaline", "noCard"));
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
	public void reportMapName(String map) {
		relayMessage(String.format(
				StringRes.getString("messaging.serversMap"),
				map));
	}

	@Override
	public void reportGameStartETA(int seconds) {
		relayMessage(String.format(
				StringRes.getString("messaging.gameStartETA"),
				seconds));
	}

	@Override
	public void reportOthersConnected(int current, int maximum) {
		relayMessage(String.format(
				StringRes.getString("messaging.othersWaiting"),
				current,
				maximum));
	}

	@Override
	public void reportTeam(String team) {
		relayMessage(String.format(
				StringRes.getString("messaging.gamemaster.playAs"),
				team));
	}

	@Override
	public void reportObjectCardDrawn(String cardname) {
		relayMessage(String.format(
				StringRes.getString("messaging.objectCardDrawn"),
				cardname));
	}

	@Override
	public void reportSuccessfulEscape() {
		relayMessage(StringRes.getString("messaging.EscapedSuccessfully"));
	}

	@Override
	public void reportStartMyTurn(String myname, String mypos) {
		relayMessage(String.format(
				StringRes.getString("messaging.hail.player"),
				myname,
				mypos));
	}

	@Override
	public void reportEndTurn() {
		relayMessage(StringRes.getString("messaging.farewell"));	
	}

	@Override
	public void reportMovementException(String exceptionMessage) {
		relayMessage(exceptionMessage);	
	}
	
	@Override
	public void reportCardException(String exceptionMessage) {
		relayMessage(exceptionMessage);	
	}

	@Override
	public void reportDefense() {
		relayMessage(StringRes.getString("messaging.defendedSuccessfully"));
		
	}

	@Override
	public void reportDiscardedCard(String cardName) {
		relayMessage(String.format(StringRes.getString("messaging.discardedCard"),cardName));
		
	}
	
}


