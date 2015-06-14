package it.escape.client.connection.rmi;

import it.escape.client.controller.cli.StateManagerCLIInterface;
import it.escape.client.view.cli.Terminal;
import it.escape.server.controller.GameMaster;
import it.escape.strings.StringRes;

/**This class implements the ClientRemoteInterface. 
 * This object is exposed to the Server.
 * In the Server, UserMessagesReporterRMI invokes remotely methods of this class.
 * These methods, in turn, invoke the appropriate methods: 
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
	public void setMap(String mapname) {
		
	}

	@Override
	public void setWholeMOTD(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visualizeChatMsg(String author, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStartETA(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTurn(int turnNumber, String playerName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renamePlayer(String previousName, String changedName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renameMyself(String myNewName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMyPosition(String myPos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMyTeam(String teamName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawnCard(String cardClassName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void discardedCard(String cardName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerDisconnected(String playerName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWinners(String team, String winnersNames) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoserTeam(String teamName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notMyTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startMyTurn(String myName, String myPos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askForMovement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void askForYesNo(String question) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askForNoisePosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public void askForLightsPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public void whichObjectCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void haveToDiscard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void askPlayOrDiscard(String question) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventObject(String playerName, String cardClassName,
			String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventAttack(String attacker, String location, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventNoise(String location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventDeath(String playerKilled, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventEndGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endResults() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventFoundPlayer(String playerName, String location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventDefense(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMovementException(String exceptionMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showWrongCardException(String exceptionMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMessageInTerminal(String message) {
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
