package it.escape.client.view.connection.rmi;

import it.escape.client.controller.gui.ClientProceduresInterface;

/**This class implements the ClientRemoteInterface.
 * This object is exposed to the Server.
 * In the Server, UserMessagesReporterRMI invokes remotely methods of this class.
 * These methods, in turn, invoke the appropriate methods inside UpdaterSwing */
public class ClientRemoteSwing implements ClientRemoteInterface {

	
	private ClientProceduresInterface updaterSwing;
	
	public ClientRemoteSwing(ClientProceduresInterface updaterSwing) {
		this.updaterSwing = updaterSwing;
	}
	
	

	@Override
	public void setMap(String mapname) {
		updaterSwing.setMap(mapname);

	}

	@Override
	public void startReadingMotd() {
		updaterSwing.startReadingMotd();

	}

	@Override
	public void visualizeChatMsg(String author, String msg) {
		updaterSwing.visualizeChatMsg(author, msg);

	}

	@Override
	public void setStartETA(String message) {
		updaterSwing.setStartETA(message);

	}

	@Override
	public void startTurn(int turnNumber, String playerName) {
		updaterSwing.startTurn(turnNumber, playerName);
	}

	@Override
	public void renamePlayer(String previousName, String changedName) {
		updaterSwing.renamePlayer(previousName, changedName);

	}

	@Override
	public void renameMyself(String myNewName) {
		updaterSwing.renameMyself(myNewName);

	}

	@Override
	public void setMyPosition(String myPos) {
		updaterSwing.setMyPosition(myPos);

	}

	@Override
	public void setMyTeam(String teamName) {
		updaterSwing.setMyTeam(teamName);

	}

	@Override
	public void drawnCard(String cardClassName) {
		updaterSwing.drawnCard(cardClassName);

	}

	@Override
	public void discardedCard(String cardName) {
		updaterSwing.discardedCard(cardName);

	}

	@Override
	public void playerDisconnected(String playerName) {
		updaterSwing.playerDisconnected(playerName);

	}

	@Override
	public void setWinners(String team, String winnersNames) {
		updaterSwing.setWinners(team, winnersNames);

	}

	@Override
	public void setLoserTeam(String teamName) {
		updaterSwing.setLoserTeam(teamName);

	}

	@Override
	public void notMyTurn() {
		updaterSwing.notMyTurn();

	}

	@Override
	public void startMyTurn(String myName, String myPos) {
		updaterSwing.startMyTurn(myName, myPos);

	}

	@Override
	public void askForMovement() {
		updaterSwing.askForMovement();

	}

	@Override
	public void askForYesNo(String question) {
		updaterSwing.askForYesNo(question);

	}

	@Override
	public void askForNoisePosition() {
		updaterSwing.askForNoisePosition();

	}

	@Override
	public void askForLightsPosition() {
		updaterSwing.askForLightsPosition();
	}

	@Override
	public void whichObjectCard() {
		updaterSwing.whichObjectCard();
	}

	@Override
	public void haveToDiscard() {
		updaterSwing.haveToDiscard();

	}

	@Override
	public void askPlayOrDiscard(String question) {
		updaterSwing.askPlayOrDiscard(question);

	}

	@Override
	public void eventObject(String playerName, String cardClassName,
			String message) {
		updaterSwing.eventObject(playerName, cardClassName, message);

	}

	@Override
	public void eventAttack(String attacker, String location, String message) {
		updaterSwing.eventAttack(attacker, location, message);

	}

	@Override
	public void eventNoise(String location) {
		updaterSwing.eventNoise(location);

	}

	@Override
	public void eventDeath(String playerKilled, String message) {
		updaterSwing.eventDeath(playerKilled, message);

	}

	@Override
	public void eventEndGame() {
		updaterSwing.eventEndGame();

	}

	@Override
	public void endResults() {
		updaterSwing.endResults();

	}

	@Override
	public void eventFoundPlayer(String playerName, String location) {
		updaterSwing.eventFoundPlayer(playerName, location);

	}

	@Override
	public void eventDefense(String message) {
		updaterSwing.eventDefense(message);

	}

	@Override
	public void showMovementException(String exceptionMessage) {
		updaterSwing.showMovementException(exceptionMessage);

	}

	@Override
	public void showWrongCardException(String exceptionMessage) {
		updaterSwing.showWrongCardException(exceptionMessage);

	}

	@Override
	public void showMessageInTerminal(String message) {
		// TODO Auto-generated method stub

	}

}
