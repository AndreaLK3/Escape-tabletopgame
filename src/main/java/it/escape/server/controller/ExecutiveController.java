package it.escape.server.controller;

import java.util.logging.Logger;

import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

public class ExecutiveController implements Runnable {
	
	protected static final Logger log = Logger.getLogger( ExecutiveController.class.getName() );
	
	private Player currentPlayer;
	
	private TimeController timeControllerRef;
	
	private TurnHandler turnHandler;
	
	private boolean runGame;
	
	public synchronized void startTurn(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		notify();
	}

	public void run() {
		log.fine(StringRes.getString("controller.executor.start"));
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

		if (currentPlayer instanceof Human) {
			turnHandler = new TurnHandlerHuman(currentPlayer);
		}
		else {
			turnHandler = new TurnHandlerAlien(currentPlayer);
		}
		turnHandler.executeTurnSequence();
}

	

	public ExecutiveController(TimeController timeControllerRef) {
		LogHelper.setDefaultOptions(log);
		this.timeControllerRef = timeControllerRef;
		runGame = true;		
	}
	
	public void fillInDefaultChoices() {
		// automatically complete the actions for this turn
		// at this point gameTurn() will return on its own
	}
}
