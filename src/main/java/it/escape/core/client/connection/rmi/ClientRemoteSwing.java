package it.escape.core.client.connection.rmi;

import java.rmi.RemoteException;

import it.escape.core.client.controller.gui.ClientProceduresInterface;

/**This class implements the ClientRemoteInterface.
 * This object is exposed to the ServerSocketCore.
 * In the ServerSocketCore, UserMessagesReporterRMI invokes remotely methods of this class.
 * These methods, in turn, invoke the appropriate methods inside UpdaterSwing */
public class ClientRemoteSwing extends RMIPingBack implements ClientRemoteInterface {

	private int id;
	
	private ClientProceduresInterface updaterSwing;
	
	public ClientRemoteSwing(ClientProceduresInterface updaterSwing) {
		super();
		this.updaterSwing = updaterSwing;
	}
	
	

	@Override
	public void setMap(String mapname) throws RemoteException {
		updaterSwing.setMap(mapname);

	}

	@Override
	public void setWholeMOTD(String text) throws RemoteException {
		updaterSwing.setWholeMOTD(text);
	}

	@Override
	public void visualizeChatMsg(String author, String msg) throws RemoteException {
		updaterSwing.visualizeChatMsg(author, msg);

	}

	@Override
	public void setStartETA(String message) throws RemoteException {
		updaterSwing.setStartETA(message);

	}

	@Override
	public void startTurn(int turnNumber, String playerName) throws RemoteException {
		updaterSwing.startTurn(turnNumber, playerName);
	}

	@Override
	public void renamePlayer(String previousName, String changedName) throws RemoteException {
		updaterSwing.renamePlayer(previousName, changedName);

	}

	@Override
	public void renameMyself(String myNewName) throws RemoteException {
		updaterSwing.renameMyself(myNewName);

	}

	@Override
	public void setMyPosition(String myPos) throws RemoteException {
		updaterSwing.setMyPosition(myPos);

	}

	@Override
	public void setMyTeam(String teamName) throws RemoteException {
		updaterSwing.setMyTeam(teamName);

	}

	@Override
	public void drawnCard(String cardClassName) throws RemoteException {
		updaterSwing.drawnCard(cardClassName);

	}

	@Override
	public void discardedCard(String cardName) throws RemoteException {
		updaterSwing.discardedCard(cardName);

	}

	@Override
	public void playerDisconnected(String playerName) throws RemoteException {
		updaterSwing.playerDisconnected(playerName);

	}

	@Override
	public void setWinners(String team, String winnersNames) throws RemoteException {
		updaterSwing.setWinners(team, winnersNames);

	}

	@Override
	public void setLoserTeam(String teamName) throws RemoteException {
		updaterSwing.setLoserTeam(teamName);

	}

	@Override
	public void notMyTurn() throws RemoteException {
		updaterSwing.notMyTurn();

	}

	@Override
	public void startMyTurn(String myName, String myPos) throws RemoteException {
		updaterSwing.startMyTurn(myName, myPos);

	}

	@Override
	public void askForMovement() throws RemoteException {
		updaterSwing.askForMovement();

	}

	@Override
	public void askForYesNo(String question) throws RemoteException {
		updaterSwing.askForYesNo(question);

	}

	@Override
	public void askForNoisePosition() throws RemoteException {
		updaterSwing.askForNoisePosition();
	}

	@Override
	public void askForLightsPosition() throws RemoteException {
		updaterSwing.askForLightsPosition();
	}

	@Override
	public void askWhichObjectCard() throws RemoteException {
		updaterSwing.askWhichObjectCard();
	}

	@Override
	public void haveToDiscard() throws RemoteException {
		updaterSwing.haveToDiscard();

	}

	@Override
	public void askPlayOrDiscard(String question) throws RemoteException {
		updaterSwing.askPlayOrDiscard(question);

	}
	
	@Override 
	public void youEscaped()throws RemoteException  {
		updaterSwing.youEscaped();
	}

	@Override
	public void eventObject(String playerName, String cardClassName) throws RemoteException {
		updaterSwing.eventObject(playerName, cardClassName);

	}

	@Override
	public void eventAttack(String attacker, String location) throws RemoteException {
		updaterSwing.eventAttack(attacker, location);

	}

	@Override
	public void eventNoise(String location) throws RemoteException {
		updaterSwing.eventNoise(location);

	}

	@Override
	public void eventDeath(String playerKilled) throws RemoteException {
		updaterSwing.eventDeath(playerKilled);

	}

	@Override
	public void eventEndGame() throws RemoteException {
		updaterSwing.eventEndGame();

	}

	@Override
	public void endResults() throws RemoteException {
		updaterSwing.endResults();

	}

	@Override
	public void eventFoundPlayer(String playerName, String location) throws RemoteException {
		updaterSwing.eventFoundPlayer(playerName, location);
	}

	@Override
	public void eventDefense(String location) throws RemoteException {
		updaterSwing.eventDefense(location);

	}

	@Override
	public void showMovementException(String exceptionMessage) throws RemoteException {
		updaterSwing.showMovementException(exceptionMessage);

	}

	@Override
	public void showWrongCardException(String exceptionMessage) throws RemoteException {
		updaterSwing.showWrongCardException(exceptionMessage);

	}

	@Override
	public void showMessageInTerminal(String message) throws RemoteException {
		// do nothing
	}

	@Override
	public void playerConnected(int current, int maximum) throws RemoteException {
		updaterSwing.playerConnected(current, maximum);
	}

	@Override
	public void playersInLobby(int current, int maximum) throws RemoteException {
		updaterSwing.playersInLobby(current, maximum);
	}


	@Override
	public void eventPlayerEscaped(String playerName) throws RemoteException {
		updaterSwing.eventPlayerEscaped(playerName);
		
	}

	@Override
	public void failedEscape() throws RemoteException {
		updaterSwing.failedEscape();
	}


	@Override
	public void setID(int clientID) throws RemoteException {
		this.id = clientID;
	}

	@Override
	public int getID() throws RemoteException {
		return id;
	}



	@Override
	public void youDefended() throws RemoteException {
		updaterSwing.youDefended();
		
	}
	


}
