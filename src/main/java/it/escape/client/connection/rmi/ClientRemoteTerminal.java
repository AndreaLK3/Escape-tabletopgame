package it.escape.client.connection.rmi;

import java.rmi.RemoteException;

import it.escape.client.controller.cli.StateManagerCLIInterface;
import it.escape.client.view.cli.Terminal;
import it.escape.server.controller.GameMaster;
import it.escape.server.model.game.players.JoinPlayerList;
import it.escape.strings.StringRes;

/**This class implements the ClientRemoteInterface. 
 * This object is exposed to the Server.
 * In the Server, UserMessagesReporterRMI invokes remotely methods of ClientRemoteInterface.
 * In turn, the implementations of the remote interface methods in this class invoke the appropriate methods: 
 * inside StateManager (to set up the TurnInputState for the format checks)
 * inside Terminal (to show messages)*/
public class ClientRemoteTerminal implements ClientRemoteInterface {

	private StateManagerCLIInterface stateManager;
	private Terminal terminal;
	private int id;
	
	/**The constructor; it initializes the stateManager and the Terminal references.*/
	public ClientRemoteTerminal(StateManagerCLIInterface stateManager, Terminal terminal) {
		this.stateManager = stateManager;
		this.terminal = terminal;
	}
	
	@Override
	public void setMap(String mapname) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.serversMap"), mapname);
		terminal.visualizeMessage(message);
	}

	@Override
	public void setWholeMOTD(String text) throws RemoteException {
		terminal.visualizeMessage(text);
	}

	@Override
	public void visualizeChatMsg(String author, String msg) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.relayChat"), author, msg);
		terminal.visualizeMessage(message);
	}

	@Override
	public void setStartETA(String message) throws RemoteException {
		terminal.visualizeMessage(message);
	}

	@Override
	public void startTurn(int turnNumber, String playerName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.timecontroller.turnNumber"), turnNumber, playerName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void renamePlayer(String previousName, String changedName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.announceRename"), previousName, changedName);
		terminal.visualizeMessage(message);

	}

	@Override
	public void renameMyself(String myNewName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.whoYouAre"), myNewName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void setMyPosition(String myPos) throws RemoteException{
		String message =  String.format(
				StringRes.getString("messaging.hereYouAre"),
				myPos);
		terminal.visualizeMessage(message);
	}

	@Override
	public void setMyTeam(String teamName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.gamemaster.playAs"), teamName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void drawnCard(String cardClassName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.objectCardDrawn"), cardClassName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void discardedCard(String cardName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.discardedCard"), cardName);
		terminal.visualizeMessage(message);

	}

	@Override
	public void playerDisconnected(String playerName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerDisconnected"), playerName);
		terminal.visualizeMessage(message);

	}

	@Override
	public void setWinners(String team, String winnersNames) throws RemoteException {
		String message = String.format(
				StringRes.getString("messaging.winnerTeam"),
				team,
				winnersNames
				);
		terminal.visualizeMessage(message);
	}

	@Override
	public void setLoserTeam(String teamName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.loserTeam"),
				teamName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void notMyTurn() throws RemoteException {
		stateManager.setFreeState();
	}

	@Override
	public void startMyTurn(String myName, String myPos) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.hail.player"), myName, myPos);
		terminal.visualizeMessage(message);

	}

	@Override
	public void askForMovement() throws RemoteException {
		String message = StringRes.getString("messaging.timeToMove");
		terminal.visualizeMessage(message);
	}

	@Override
	public void askForYesNo(String question) throws RemoteException {
		stateManager.setYesNoState();
		terminal.visualizeMessage(question);

	}

	@Override
	public void askForNoisePosition() throws RemoteException {
		String message = StringRes.getString("messaging.askForNoisePosition");
		stateManager.setPositionState();
		terminal.visualizeMessage(message);
	}

	@Override
	public void askForLightsPosition() throws RemoteException {
		stateManager.setPositionState();
		terminal.visualizeMessage(StringRes.getString("messaging.askForLightsPosition"));
	}

	@Override
	public void askWhichObjectCard() throws RemoteException {
		stateManager.setObjectCardState();
		terminal.visualizeMessage(StringRes.getString("messaging.askWhichObjectCard"));
	}

	@Override
	public void haveToDiscard() throws RemoteException {
		terminal.visualizeMessage(StringRes.getString("messaging.tooManyCardsAlien"));

	}

	@Override
	public void askPlayOrDiscard(String question) throws RemoteException {
		stateManager.setOtherChoice();  // now we can handle it too
		terminal.visualizeMessage(question);
	}

	@Override
	public void eventObject(String playerName, String cardClassName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerIsUsingObjCard"),
				playerName,cardClassName,playerName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void eventAttack(String attacker, String location) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerAttacking"),
				attacker,
				location);
		terminal.visualizeMessage(message);
	}

	@Override
	public void eventNoise(String location) throws RemoteException {
		String msg = String.format(StringRes.getString("messaging.noise"), location);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void eventDeath(String playerKilled) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerDied"),
				playerKilled);
		terminal.visualizeMessage(message);
	}

	@Override
	public void eventEndGame() throws RemoteException {
		// TODO print something

	}

	@Override
	public void endResults() throws RemoteException {
		// TODO print something

	}

	@Override
	public void eventFoundPlayer(String playerName, String location) throws RemoteException {
		String msg = String.format(StringRes.getString("messaging.disclosePlayerPosition"), playerName, location);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void eventDefense(String location) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerDefended"),
				location);
		terminal.visualizeMessage(message);
	}

	@Override
	public void showMovementException(String exceptionMessage) throws RemoteException {
		terminal.visualizeMessage(exceptionMessage);

	}

	@Override
	public void showWrongCardException(String exceptionMessage) throws RemoteException {
		terminal.visualizeMessage(exceptionMessage);

	}

	@Override
	public void showMessageInTerminal(String message) throws RemoteException {
		terminal.visualizeMessage(message);
	}

	@Override
	public void playerConnected(int current, int maximum) throws RemoteException {
		String msg = String.format(StringRes.getString("messaging.playerConnected"),
				current,
				maximum);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void playersInLobby(int current, int maximum) throws RemoteException {
		String msg = String.format(
				StringRes.getString("messaging.othersWaiting"),
				current,
				maximum);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void eventPlayerEscaped(String playerName) throws RemoteException {
		String message = String.format(StringRes.getString("messaging.playerEscaped"),
				playerName,
				StringRes.getString("ship_name"));
		stateManager.setFreeState();
		terminal.visualizeMessage(message);
		
	}

	@Override
	public void youEscaped() throws RemoteException {
		terminal.visualizeMessage(StringRes.getString("messaging.EscapedSuccessfully"));
		
	}

	@Override
	public void failedEscape() throws RemoteException {
		terminal.visualizeMessage(StringRes.getString("messaging.EscapeHatchDoesNotWork"));
	}
	
	@Override
	public void setID(int clientID) throws RemoteException {
		this.id = clientID;
	}

	@Override
	public int getID() throws RemoteException {
		return id;
	}

}
