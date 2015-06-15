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
	public void discardedCard(String cardName) {
		String message = String.format(StringRes.getString("messaging.discardedCard"), cardName);
		terminal.visualizeMessage(message);

	}

	@Override
	public void playerDisconnected(String playerName) {
		String message = String.format(StringRes.getString("messaging.playerDisconnected"), playerName);
		terminal.visualizeMessage(message);

	}

	@Override
	public void setWinners(String team, String winnersNames) {
		String message = String.format(
				StringRes.getString("messaging.winnerTeam"),
				team,
				winnersNames
				);
		terminal.visualizeMessage(message);
	}

	@Override
	public void setLoserTeam(String teamName) {
		String message = String.format(StringRes.getString("messaging.loserTeam"),
				teamName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void notMyTurn() {
		stateManager.setFreeState();
	}

	@Override
	public void startMyTurn(String myName, String myPos) {
		String message = String.format(StringRes.getString("messaging.hail.player"), myName, myPos);
		terminal.visualizeMessage(message);

	}

	@Override
	public void askForMovement() {
		String message = StringRes.getString("messaging.timeToMove");
		terminal.visualizeMessage(message);
	}

	@Override
	public void askForYesNo(String question) {
		stateManager.setYesNoState();
		terminal.visualizeMessage(question);

	}

	@Override
	public void askForNoisePosition() {
		String message = StringRes.getString("messaging.askForNoisePosition");
		stateManager.setPositionState();
		terminal.visualizeMessage(message);
	}

	@Override
	public void askForLightsPosition() {
		stateManager.setPositionState();
		terminal.visualizeMessage(StringRes.getString("messaging.askForLightsPosition"));
	}

	@Override
	public void whichObjectCard() {
		stateManager.setObjectCardState();
		terminal.visualizeMessage(StringRes.getString("messaging.askWhichObjectCard"));
	}

	@Override
	public void haveToDiscard() {
		terminal.visualizeMessage(StringRes.getString("messaging.tooManyCardsAlien"));

	}

	@Override
	public void askPlayOrDiscard(String question) {
		stateManager.setOtherChoice();  // now we can handle it too
		terminal.visualizeMessage(question);
	}

	@Override
	public void eventObject(String playerName, String cardClassName, String message) {
		terminal.visualizeMessage(message);
	}

	@Override
	public void eventAttack(String attacker, String location, String message) {
		terminal.visualizeMessage(message);
	}

	@Override
	public void eventNoise(String location) {
		String msg = String.format(StringRes.getString("messaging.noise"), location);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void eventDeath(String playerKilled, String message) {
		terminal.visualizeMessage(message);
	}

	@Override
	public void eventEndGame() {
		// TODO print something

	}

	@Override
	public void endResults() {
		// TODO print something

	}

	@Override
	public void eventFoundPlayer(String playerName, String location) {
		String msg = String.format(StringRes.getString("messaging.disclosePlayerPosition"), playerName, location);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void eventDefense(String message) {
		terminal.visualizeMessage(message);
	}

	@Override
	public void showMovementException(String exceptionMessage) {
		terminal.visualizeMessage(exceptionMessage);

	}

	@Override
	public void showWrongCardException(String exceptionMessage) {
		terminal.visualizeMessage(exceptionMessage);

	}

	@Override
	public void showMessageInTerminal(String message) throws RemoteException {
		terminal.visualizeMessage(message);
	}

	@Override
	public void playerConnected(int current, int maximum) {
		String msg = String.format(StringRes.getString("messaging.playerConnected"),
				current,
				maximum);
		terminal.visualizeMessage(msg);
	}

	@Override
	public void playersInLobby(int current, int maximum) {
		String msg = String.format(
				StringRes.getString("messaging.othersWaiting"),
				current,
				maximum);
		terminal.visualizeMessage(msg);
	}

}
