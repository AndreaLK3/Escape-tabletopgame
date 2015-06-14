package it.escape.server.controller;

import it.escape.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.server.view.MessagingChannelInterface;
import it.escape.server.view.MessagingChannelRMI;
import it.escape.strings.StringRes;

public class UserMessagesReporterRMI extends UserMessagesReporter {
	
	private MessagingChannelRMI interfaceWithUser;
	
	public UserMessagesReporterRMI(MessagingChannelRMI interfaceWithUser) {
		this.interfaceWithUser = interfaceWithUser;
	}
	
	@Override
	public MessagingChannelInterface getInterfaceWithUser() {
		return interfaceWithUser;
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
		interfaceWithUser.getClient().showMessageInTerminal(string);
	}

	@Override
	public void reportMapName(String map) {
		interfaceWithUser.getClient().setMap(map);
	}

	@Override
	public void reportGameStartETA(int seconds) {
		interfaceWithUser.getClient().setStartETA(String.format(
					StringRes.getString("messaging.gameStartETA"),
					seconds));
	}

}
