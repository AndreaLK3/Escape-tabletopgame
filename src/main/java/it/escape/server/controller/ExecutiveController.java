package it.escape.server.controller;

import it.escape.server.model.game.PlayerTeams;

public class ExecutiveController implements Runnable {
	
	private Player currentPlayer;
	
	private TimeController timeControllerRef;
	
	public void startTurn(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		notify();
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	private void gameTurn() {
		try {
			wait();  // wait to be awakened by startTurn()
		} catch (InterruptedException e) {
		}
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
		// istanzia gestri turni
	}
	
	
}
