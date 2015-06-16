package it.escape.server.controller;

import java.rmi.RemoteException;

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
		log.finer("Overriding default once...");
		interfaceWithUser.overrideDefault();
	}

	@Override
	public void reportMyUserPosition(String position) {
		try {
			interfaceWithUser.getClient().setMyPosition(position);
		} catch (RemoteException e) {
			log.warning("Cannot report my user position: " + e.getMessage());
		}
	}

	@Override
	public boolean askIfObjectCard() {
		return askYesNo(StringRes.getString("messaging.askPlayObjectCard"));
	}
	
	private boolean askYesNo(String message) {
		String defaultChoice = "no";
		
		if (automaticOverriding) {
			log.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askForYesNo(message);
			} catch (RemoteException e) {
				log.warning("Cannot ask 'yes or no': " + e.getMessage());
			}
			String answer = interfaceWithUser.getAnswer();
			if (answer.equals("yes")) {
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean askIfAttack() {
		return askYesNo(StringRes.getString("messaging.askIfAttack"));
	}

	@Override
	public boolean askPlayCardOrDiscard() {
		String defaultChoice = "discard";
		
		if (automaticOverriding) {
			log.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askPlayOrDiscard(StringRes.getString("messaging.tooManyCardsHuman"));
			} catch (RemoteException e) {
				log.warning("Cannot ask 'play or discard': " + e.getMessage());
			}
			String answer = interfaceWithUser.getAnswer();
			if (answer.equals("play")) {
				return true;
			}
			return false;
		}
	}
	
	public void reportAskDiscard() {
		try {
			interfaceWithUser.getClient().haveToDiscard();
		} catch (RemoteException e) {
			log.warning("Cannot ask to discard a card: " + e.getMessage());
		}
	}

	@Override
	public String askWhichObjectCard(String defaultCard) {

		return null;
	}

	@Override
	public MoveCommand askForMovement(String playerCurrentPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String askForNoisePosition(String playerCurrentPos) {
		String defaultChoice = playerCurrentPos;
		
		if (automaticOverriding) {
			log.finer("automaticOverriding: return " + defaultChoice);
			return defaultChoice;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askForNoisePosition();
			} catch (RemoteException e) {
				log.warning("Cannot ask 'yes or no': " + e.getMessage());
			}
			String answer = interfaceWithUser.getAnswer();
			return answer;
		}
	}

	@Override
	public String askForLightsPosition(String playerCurrentPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void relayMessage(String string) {
		try {
			interfaceWithUser.getClient().showMessageInTerminal(string);
		} catch (RemoteException e) {
			log.warning("Cannot relay the message: " + e.getMessage());
		}
	}

	@Override
	public void reportMapName(String map) {
		try {
			interfaceWithUser.getClient().setMap(map);
		} catch (RemoteException e) {
			log.warning("Cannot set the map: " + e.getMessage());
		}
	}

	@Override
	public void reportGameStartETA(int seconds) {
		try {
			interfaceWithUser.getClient().setStartETA(String.format(
						StringRes.getString("messaging.gameStartETA"),
						seconds));
		} catch (RemoteException e) {
			log.warning("Cannot report game start ETA: " + e.getMessage());
		}
	}

	@Override
	public void reportOthersConnected(int current, int maximum) {
		try {
			interfaceWithUser.getClient().playersInLobby(current, maximum);
		} catch (RemoteException e) {
			log.warning("Cannot report number of connectd players: " + e.getMessage());
		}
	}

	@Override
	public void reportTeam(String team) {
		try {
			interfaceWithUser.getClient().setMyTeam(team);
		} catch (RemoteException e) {
			log.warning("Cannot report team name: " + e.getMessage());
		}
	}

	@Override
	public void reportObjectCardDrawn(String cardname) {
		try {
			interfaceWithUser.getClient().drawnCard(cardname);
		} catch (RemoteException e) {
			log.warning("Cannot report object card drawn: " + e.getMessage());
		}
	}

	@Override
	public void reportSuccessfulEscape() {
		try {
			interfaceWithUser.getClient().youEscaped();
		} catch (RemoteException e) {
			log.warning("Cannot report object card drawn: " + e.getMessage());
		}
		
	}

}
