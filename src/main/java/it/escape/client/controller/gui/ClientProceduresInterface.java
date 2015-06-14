package it.escape.client.controller.gui;

public interface ClientProceduresInterface {

	public abstract void setMap(String mapname);

	public abstract void setWholeMOTD(String text);

	public abstract void visualizeChatMsg(String author, String msg);

	public abstract void setStartETA(String message);

	public abstract void startTurn(int turnNumber, String playerName);

	public abstract void renamePlayer(String previousName, String changedName);

	public abstract void renameMyself(String myNewName);

	public abstract void setMyPosition(String myPos);

	public abstract void setMyTeam(String teamName);

	public abstract void drawnCard(String cardClassName);

	public abstract void discardedCard(String cardName);
	
	public abstract void playerConnected(int current, int maximum);

	public abstract void playerDisconnected(String playerName);

	public abstract void setWinners(String team, String winnersNames);

	public abstract void setLoserTeam(String teamName);

	public abstract void notMyTurn();

	public abstract void startMyTurn(String myName, String myPos);

	public abstract void askForMovement();

	public abstract void askForYesNo(String question);

	public abstract void askForNoisePosition();

	public abstract void askForLightsPosition();

	public abstract void whichObjectCard();

	public abstract void haveToDiscard();

	public abstract void askPlayOrDiscard(String question);

	public abstract void eventObject(String playerName, String cardClassName,
			String message);

	public abstract void eventAttack(String attacker, String location,
			String message);

	public abstract void eventNoise(String location);

	public abstract void eventDeath(String playerKilled, String message);

	public abstract void eventEndGame();

	public abstract void endResults();

	public abstract void eventFoundPlayer(String playerName, String location);

	public abstract void eventDefense(String message);

	public abstract void showMovementException(String exceptionMessage);

	public abstract void showWrongCardException(String exceptionMessage);
	
	
}