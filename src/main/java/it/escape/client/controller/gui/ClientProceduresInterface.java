package it.escape.client.controller.gui;

import java.rmi.RemoteException;
// TODO: very big: all those methods must throw RemoteException to be exported by RMI;
// this won't change anything in the socket routines (except adding a 
// very paranoid try-catch in UpdaterSwing)
public interface ClientProceduresInterface {

	public abstract void setMap(String mapname) throws RemoteException;

	public abstract void setWholeMOTD(String text) throws RemoteException;

	public abstract void visualizeChatMsg(String author, String msg) throws RemoteException;

	public abstract void setStartETA(String message) throws RemoteException;

	public abstract void startTurn(int turnNumber, String playerName) throws RemoteException;

	public abstract void renamePlayer(String previousName, String changedName) throws RemoteException;

	public abstract void renameMyself(String myNewName) throws RemoteException;

	/** This is used to receive my position from the server.
	 * It's called directly after a successfule move or
	 * whoami command */
	public abstract void setMyPosition(String myPos) throws RemoteException;

	public abstract void setMyTeam(String teamName) throws RemoteException;

	public abstract void drawnCard(String cardClassName) throws RemoteException;

	public abstract void discardedCard(String cardName) throws RemoteException;
	
	public abstract void playerConnected(int current, int maximum) throws RemoteException;
	
	public abstract void playersInLobby(int current, int maximum) throws RemoteException;

	public abstract void playerDisconnected(String playerName) throws RemoteException;

	public abstract void setWinners(String team, String winnersNames) throws RemoteException;

	public abstract void setLoserTeam(String teamName) throws RemoteException;

	public abstract void notMyTurn() throws RemoteException;

	public abstract void startMyTurn(String myName, String myPos) throws RemoteException;

	public abstract void askForMovement() throws RemoteException;

	public abstract void askForYesNo(String question) throws RemoteException;

	public abstract void askForNoisePosition() throws RemoteException;

	public abstract void askForLightsPosition() throws RemoteException;

	public abstract void whichObjectCard() throws RemoteException;

	public abstract void haveToDiscard() throws RemoteException;

	public abstract void askPlayOrDiscard(String question) throws RemoteException;

	public abstract void eventObject(String playerName, String cardClassName,
			String message) throws RemoteException;

	public abstract void eventAttack(String attacker, String location) throws RemoteException;

	public abstract void eventNoise(String location) throws RemoteException;

	public abstract void eventDeath(String playerKilled) throws RemoteException;

	public abstract void eventEndGame() throws RemoteException;

	public abstract void endResults() throws RemoteException;

	public abstract void eventFoundPlayer(String playerName, String location) throws RemoteException;

	public abstract void eventDefense(String message) throws RemoteException;
	
	public void eventPlayerEscaped(String playerName) throws RemoteException;

	public abstract void showMovementException(String exceptionMessage) throws RemoteException;

	public abstract void showWrongCardException(String exceptionMessage) throws RemoteException;

	public abstract void youEscaped() throws RemoteException;
	
	public abstract void failedEscape() throws RemoteException;
	
	
}