package it.escape.server.controller;

import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannelRMI;
import it.escape.server.view.MessagingChannelStrings;
import it.escape.strings.StringRes;

public class UserMessagesReporterRMI extends UserMessagesReporter {
	
	private MessagingChannelRMI interfaceWithUser;
	
	public UserMessagesReporterRMI(MessagingChannelRMI interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
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
	public MessagingChannelStrings getInterfaceWithUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Announcer getAnnouncer() {
		// TODO Auto-generated method stub
		return null;
	}

}
