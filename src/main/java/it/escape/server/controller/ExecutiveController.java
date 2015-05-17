package it.escape.server.controller;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.character.Player;

public class ExecutiveController implements Runnable {
	
	private Player currentPlayer;
	
	private TimeController timeControllerRef;
	
	private TurnHandler turnHandler;
	
	private boolean runGame;
	
	public synchronized void startTurn(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		notify();
	}

	public void run() {
		gameLoop();
	}
	
	/** Note: adding "synchronize" in a code block where there is a wait() or notify() function is mandatory*/
	private synchronized void gameLoop() {
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
		PlayerTeams team = currentPlayer.getTeam();
		if (team == PlayerTeams.HUMANS) {
			turnHandler = new TurnHandlerHuman(currentPlayer);
		}
		else {
			turnHandler = new TurnHandlerAlien(currentPlayer);
		}
		turnHandler.turnSequence();
	}

	public ExecutiveController(TimeController timeControllerRef) {
		this.timeControllerRef = timeControllerRef;
		runGame = true;		
	}
	
	public void fillInDefaultChoices() {
		// automatically complete the actions for this turn
		// at this point gameTurn() will return on its own
	}
}
