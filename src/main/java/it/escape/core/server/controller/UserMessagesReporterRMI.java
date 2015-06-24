package it.escape.core.server.controller;

import it.escape.core.server.controller.game.actions.playercommands.MoveCommand;
import it.escape.core.server.view.MessagingChannelInterface;
import it.escape.core.server.view.MessagingChannelRMI;
import it.escape.tools.strings.StringRes;

import java.rmi.RemoteException;
import java.util.logging.Level;

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
		LOGGER.finer("Overriding default once...");
		interfaceWithUser.overrideDefault();
	}

	@Override
	public void reportMyUserPosition(String position) {
		try {
			interfaceWithUser.getClient().setMyPosition(position);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report my user position", e);
		}
	}

	@Override
	public boolean askIfObjectCard() {
		return askYesNo(StringRes.getString("messaging.askPlayObjectCard"));
	}
	
	private boolean askYesNo(String message) {
		String defaultChoice = "no";
		
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askForYesNo(message);
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Cannot ask 'yes or no'", e);
			}
			String answer = interfaceWithUser.getAnswer();
			if ("yes".equals(answer)) {
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
			LOGGER.finer("automaticOverriding: return false");
			return false;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askPlayOrDiscard(StringRes.getString("messaging.tooManyCardsHuman"));
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Cannot ask 'play or discard'", e);
			}
			String answer = interfaceWithUser.getAnswer();
			if ("play".equals(answer)) {
				return true;
			}
			return false;
		}
	}
	
	public void reportHaveToDiscard() {
		try {
			interfaceWithUser.getClient().haveToDiscard();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot ask to discard a card", e);
		}
	}

	@Override
	public String askWhichObjectCard(String defaultCard) {
		String defaultChoice = defaultCard;
		
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return " + defaultChoice);
			return defaultChoice;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			String card;
			try {
				interfaceWithUser.getClient().askWhichObjectCard();
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Cannot ask for object card", e);
			}
			card = interfaceWithUser.getAnswer();
			return card;
		}
	}

	@Override
	public MoveCommand askForMovement(String playerCurrentPos) {
		String defaultChoice = playerCurrentPos;
		
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return " + defaultChoice);
			return new MoveCommand(defaultChoice);
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			String destination;
			try {
				interfaceWithUser.getClient().askForMovement();
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Cannot ask for movement", e);
			}
			destination = interfaceWithUser.getAnswer();
			return new MoveCommand(destination);
		}
	}

	@Override
	public String askForNoisePosition(String playerCurrentPos) {
		String defaultChoice = playerCurrentPos;
		
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return " + defaultChoice);
			return defaultChoice;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askForNoisePosition();
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Cannot ask for noise", e);
			}
			String answer = interfaceWithUser.getAnswer();
			return answer;
		}
	}

	@Override
	public String askForLightsPosition(String playerCurrentPos) {
		String defaultChoice = playerCurrentPos;
		
		if (automaticOverriding) {
			LOGGER.finer("automaticOverriding: return " + defaultChoice);
			return defaultChoice;
		} else {
			interfaceWithUser.setDefaultOption(defaultChoice);
			try {
				interfaceWithUser.getClient().askForLightsPosition();
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Cannot ask for lights", e);
			}
			String answer = interfaceWithUser.getAnswer();
			return answer;
		}
	}

	@Override
	public void relayMessage(String string) {
		try {
			interfaceWithUser.getClient().showMessageInTerminal(string);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot relay the message", e);
		}
	}

	@Override
	public void reportMapName(String map) {
		try {
			interfaceWithUser.getClient().setMap(map);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot set the map", e);
		}
	}

	@Override
	public void reportGameStartETA(int seconds) {
		try {
			interfaceWithUser.getClient().setStartETA(seconds);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report game start ETA", e);
		}
	}

	@Override
	public void reportOthersConnected(int current, int maximum) {
		try {
			interfaceWithUser.getClient().playersInLobby(current, maximum);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report number of connectd players", e);
		}
	}

	@Override
	public void reportTeam(String team) {
		try {
			interfaceWithUser.getClient().setMyTeam(team);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report team name", e);
		}
	}

	@Override
	public void reportObjectCardDrawn(String cardname) {
		try {
			interfaceWithUser.getClient().drawnCard(cardname);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report object card draw", e);
		}
	}

	@Override
	public void reportSuccessfulEscape() {
		try {
			interfaceWithUser.getClient().youEscaped();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report object card drawn", e);
		}
		
	}

	@Override
	public void reportStartMyTurn(String myname, String mypos) {
		try {
			interfaceWithUser.getClient().startMyTurn(myname, mypos);;
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report start my turn", e);
		}
	}

	@Override
	public void reportEndTurn() {
		try {
			interfaceWithUser.getClient().notMyTurn();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Cannot report: my Turn has ended", e);
		}
		
	}

	@Override
	public void reportMovementException(String exceptionMessage) {
		try {
			interfaceWithUser.getClient().showMovementException(exceptionMessage);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Could not report: movement Exception", e);
		}
		
	}

	@Override
	public void reportCardException(String exceptionMessage) {
		try {
			interfaceWithUser.getClient().showWrongCardException(exceptionMessage);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Could not report: card Exception", e);
		}
		
	}

	@Override
	public void reportDefense() {
		try {
			interfaceWithUser.getClient().youDefended();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Could not report: a player defended himself", e);
		}
		
	}

	@Override
	public void reportDiscardedCard(String cardName) {
		try {
			interfaceWithUser.getClient().discardedCard(cardName);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Could not report: a player discarded a card", e);
		}
		
	}

}
