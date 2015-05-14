package it.escape.server.controller;

import it.escape.server.model.game.PlayerTeams;

public class ExecutiveController implements Runnable {
	
	private Player currentPlayer;
	
	private TimeController timeControllerRef;
	
	private boolean runGame;
	
	public void startTurn(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		notify();
	}

	public void run() {
		gameLoop();
	}
	
	private void gameLoop() {
		while (runGame) {
			try {
				wait();  // wait to be awakened by startTurn()
			} catch (InterruptedException e) {
			}
			gameTurn();
			timeControllerRef.endTurn();  // wake up timeController, prevents timeout
		}
	}

	private void gameTurn() {
		PlayerTeams team = currentPlayer.getCharacter().getTeam();
		if (team == PlayerTeams.HUMANS) {
			// lancia il gestore turni umano
		}
		else {
			// lancia il gestore turni alieno
		}
	}

	public ExecutiveController(TimeController timeControllerRef) {
		this.timeControllerRef = timeControllerRef;
		runGame = true;
		// istanzia gestri turni
	}
	
	public void fillInDefaultChoices() {
		// automatically complete the actions for this turn
		// at this point gameTurn() will return on its own
	}
}
