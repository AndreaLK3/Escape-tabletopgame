package it.escape.client.view;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.escape.GlobalSettings;
import it.escape.client.ClientLocalSettings;
import it.escape.client.ClientRemoteInterface;
import it.escape.client.controller.cli.StateManagerCLIInterface;
import it.escape.client.controller.gui.ClientProceduresInterface;
import it.escape.rmitestbed.ExampleClient;
import it.escape.rmitestbed.ExampleServerRemote;
import it.escape.server.view.ServerRemoteInterface;

/**This class implements the ClientRemoteInterface, and it is the object that is 
 * exposed to the Server*/
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
	public void setFreeState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setObjectCardState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPositionState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setYesNoState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMyTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNotMyTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMessageInTerminal(String message) {
		// TODO Auto-generated method stub

	}

}
