package it.escape.server.controller;

import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.List;
import java.util.logging.Logger;

public class TimeController implements Runnable {
	
	protected static final Logger log = Logger.getLogger( TimeController.class.getName() );
	
	private final static Integer TIMEOUT = 60000;  // milliseconds
	
	private final static int MAX_TURNS = 39;
	
	private ExecutiveController executorRef;
	
	private boolean runGame;
	
	private boolean turnCompleted;
	
	private List<Player> turnOrder; // reference to the ordered list of players
	private int nowPlaying;
	
	private int turnNumber;
	
	public synchronized void endTurn() {
		turnCompleted = true;
		notify();
	}

	public void run() {
		log.fine(StringRes.getString("controller.time.start"));
		turnNumber = 0;
		mainLoop();
		log.fine(StringRes.getString("controller.time.finish"));
	}

	public TimeController(List<Player> turnOrder) {
		LogHelper.setDefaultOptions(log);
		this.turnOrder = turnOrder;
		nowPlaying = 1;
		this.runGame = true;
	}

	public void bindExecutor(ExecutiveController executorRef) {
		this.executorRef = executorRef;
	}
	
	private synchronized void mainLoop() {
		while (runGame) {
			turnCompleted = false;
			Player current = turnOrder.get(nowPlaying);
			executorRef.startTurn(current);  // run current player's turn
			
			try {
				wait(TIMEOUT);  // wait for it to end
			} catch (InterruptedException e) {
			}
			
			if (!turnCompleted) {  // didn't end in time? End it for him
				log.fine(StringRes.getString("controller.time.forcingDefaults"));
				executorRef.fillInDefaultChoices();
				
				try {
					/*
					 * the executiveController will now conclude the turn
					 * properly, and notify our timeController; we catch the
					 * notify and go on as usual.
					 */
					wait();
				} catch (InterruptedException e) {
				}
			}
			intermediateVictoryCheck();  // game might terminate here
			nowPlaying++;  // update current player
			if (nowPlaying >= turnOrder.size()) {
				nowPlaying = 0;
				turnNumber++;
				if (turnNumber > MAX_TURNS) {
					endGame();  // ran out of turns
				}
			}
		}
	}
	
	private void intermediateVictoryCheck() {
		if (new VictoryChecker(turnOrder).isVictoryCondition()) {
			endGame();
		}
	}
	
	private void endGame() {
		runGame = false;
		executorRef.endGame();
	}
}
