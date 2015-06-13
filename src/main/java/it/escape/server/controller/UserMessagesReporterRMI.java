package it.escape.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannel;
import it.escape.server.view.MessagingChannelRMI;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

public class UserMessagesReporterRMI implements UserMessagesReporter {


	protected static final Logger log = Logger.getLogger(UserMessagesReporterRMI.class.getName() );
	
	private static List<UserMessagesReporterRMI> reportersList = new ArrayList<UserMessagesReporterRMI>();
	
	private MessagingChannelRMI interfaceWithUser;
	
	private Player thePlayer;
	
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
	
	/**Constructor */
	private UserMessagesReporterRMI(MessagingChannelRMI interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}

	public static void createUMR(MessagingChannelRMI interfaceWithUser) {
		if (reportersList.size() <= 0) {
			LogHelper.setDefaultOptions(log);
		}
		reportersList.add(new UserMessagesReporterRMI(interfaceWithUser));
	}
	
	
	/**This method assigns a player (already initialized) to an existing
	 * userMessagesReporter connected to an existing interface.
	 * @param newP
	 * @param interfaceWithUser
	 */
	public static void bindPlayer(Player newP,MessagingChannel interfaceWithUser) {
		for (UserMessagesReporterRMI r : reportersList) {	
			if (r.getInterfaceWithUser() == interfaceWithUser) {
				r.setThePlayer(newP);
				break;
			}
		}
	}
	
	private void setThePlayer(Player thePlayer) {
		this.thePlayer = thePlayer;
	}

	@Override
	public void bindAnnouncer(Announcer announcer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillinDefaultOnce() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillinDefaultAlways() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFillingDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reportMyUserPosition(String position) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean askIfObjectCard() {
		String defaultChoice = "no";
		
		if (automaticOverriding) {
			log.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			interfaceWithUser.getClient().askForYesNo(StringRes.getString("messaging.askPlayObjectCard"));
			String answer = interfaceWithUser.getAnswer();
			if (answer.equals("yes")) {
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean askIfAttack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean askPlayCardOrDiscard() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String askWhichObjectCard(String defaultCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveCommand askForMovement(String playerCurrentPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String askForNoisePosition(String playerCurrentPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String askForLightsPosition(String playerCurrentPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void relayMessage(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public Player getThePlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessagingChannel getInterfaceWithUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Announcer getAnnouncer() {
		// TODO Auto-generated method stub
		return null;
	}

}
