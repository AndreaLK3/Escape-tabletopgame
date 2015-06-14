package it.escape.client.connection.rmi;

import it.escape.client.controller.cli.StateManagerCLIInterface;
import it.escape.client.view.cli.Terminal;
import it.escape.server.controller.GameMaster;
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
	public void setMap(String mapname) {
		String message = String.format(StringRes.getString("messaging.serversMap"), mapname);
		showMessageInTerminal(message);
	}

	@Override
	public void setWholeMOTD(String text) {
		showMessageInTerminal(text);
	}

	@Override
	public void visualizeChatMsg(String author, String msg) {
		String message = String.format(StringRes.getString("messaging.relayChat"), author, msg);
		showMessageInTerminal(message);
	}

	@Override
	public void setStartETA(String message) {
		showMessageInTerminal(message);
	}

	@Override
	public void startTurn(int turnNumber, String playerName) {
		String message = String.format(StringRes.getString("messaging.timecontroller.turnNumber"), turnNumber, playerName);
		terminal.visualizeMessage(message);
	}

	@Override
	public void renamePlayer(String previousName, String changedName) {
		String message = String.format(StringRes.getString("messaging.announceRename"), previousName, changedName);
		terminal.visualizeMessage(message);

	}

	@Override
	public void renameMyself(String myNewName) {
		String message = String.format(StringRes.getString("messaging.whoYouAre"), myNewName);
		terminal.visualizeMessage(message);
	}

	/**This method is not used here; it is used in ClientRemoteSwing to update the Client's Model*/
	@Override
	public void setMyPosition(String myPos){
		//TODO: Is it ever invoked from outside in UpdaterSwing, or
		//is it used only nternally, such as getGUICardKey? in that case, it can be removed from this interface
	}

	@Override
	public void setMyTeam(String teamName) {
		String message = String.format(StringRes.getString("messaging.gamemaster.playAs"), teamName);
		showMessageInTerminal(message);
	}

	@Override
	public void drawnCard(String cardClassName) {
		String message = String.format(StringRes.getString("messaging.objectCardDrawn"), cardClassName);
		showMessageInTerminal(message);
	}

	@Override
	public void discardedCard(String cardName) {
		String message = String.format(StringRes.getString("messaging.discardedCard"), cardName);
		showMessageInTerminal(message);

	}

	@Override
	public void playerDisconnected(String playerName) {
		String message = String.format(StringRes.getString("messaging.playerDisconnected"), playerName);
		showMessageInTerminal(message);

	}

	@Override
	public void setWinners(String team, String winnersNames) {
		// TODO : This method is not used at all here, if the end results are already printed...
		//Otherwise, we can compose a message here and send it separately

	}

	@Override
	public void setLoserTeam(String teamName) {
		// TODO : This method is not used at all here, if the end results are already printed...

	}

	/**This method is not used at all when the client has the Terminal; in UpdaterSwing, it 
	 * performs some operations on the Client's Model*/
	@Override
	public void notMyTurn() {
		//TODO: Is it ever invoked from outside in UpdaterSwing, or
		//is it used only internally, such as getGUICardKey? in that case, it can be removed from this interface
	}

	@Override
	public void startMyTurn(String myName, String myPos) {
		String message = String.format(StringRes.getString("messaging.hail.player"), myName, myPos);
		showMessageInTerminal(message);

	}

	@Override
	public void askForMovement() {
		String message = StringRes.getString("messaging.timeToMove");
		showMessageInTerminal(message);
	}

	@Override
	public void askForYesNo(String question) {
		stateManager.setYesNoState();
		showMessageInTerminal(question);

	}

	@Override
	public void askForNoisePosition() {
		String message = StringRes.getString("messaging.askForNoisePosition");
		stateManager.setPositionState();
		showMessageInTerminal(message);
	}

	@Override
	public void askForLightsPosition() {
		stateManager.setPositionState();
		showMessageInTerminal(StringRes.getString("messaging.askForLightsPosition"));
	}

	@Override
	public void whichObjectCard() {
		stateManager.setObjectCardState();
		showMessageInTerminal(StringRes.getString("messaging.askWhichObjectCard"));
	}

	@Override
	public void haveToDiscard() {
		showMessageInTerminal(StringRes.getString("messaging.tooManyCardsAlien"));

	}

	@Override
	public void askPlayOrDiscard(String question) {
		stateManager.setYesNoState();
		//TODO: How did we handle the Play vs Discard prompt and format, if we handled it at all?
		showMessageInTerminal(question);
	}

	@Override
	public void eventObject(String playerName, String cardClassName, String message) {
		showMessageInTerminal(message);
	}

	@Override
	public void eventAttack(String attacker, String location, String message) {
		showMessageInTerminal(message);
	}

	@Override
	public void eventNoise(String location) {
		String msg = String.format(StringRes.getString("messaging.noise"), location);
		showMessageInTerminal(msg);
	}

	@Override
	public void eventDeath(String playerKilled, String message) {
		showMessageInTerminal(message);
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
		String msg = String.format(StringRes.getString("messaging.disclosePlayerPosition"), playerName, location);
		showMessageInTerminal(msg);
	}

	@Override
	public void eventDefense(String message) {
		showMessageInTerminal(message);
	}

	@Override
	public void showMovementException(String exceptionMessage) {
		showMessageInTerminal(exceptionMessage);

	}

	@Override
	public void showWrongCardException(String exceptionMessage) {
		showMessageInTerminal(exceptionMessage);

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
