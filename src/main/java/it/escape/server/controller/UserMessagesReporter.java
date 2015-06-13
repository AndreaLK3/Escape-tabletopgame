package it.escape.server.controller;

import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannel;

public interface UserMessagesReporter {

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

	/* E' utile? TurnHandler ha già un riferimento al Player*/
	public abstract Player getThePlayer();

	public abstract MessagingChannel getInterfaceWithUser();

	public abstract Announcer getAnnouncer();

}