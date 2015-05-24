package it.escape.server.controller;

import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.List;
import java.util.logging.Logger;

public class TimeController implements Runnable {
	
	protected static final Logger log = Logger.getLogger( TimeController.class.getName() );
	
	private final Integer TIMEOUT = 60000;  // milliseconds
	
	private ExecutiveController executorRef;
	
	private boolean runGame;
	
	private boolean turnCompleted;
	
	private List<Player> turnOrder; // reference to the ordered list of players
	private int nowPlaying;
	
	public synchronized void endTurn() {
		turnCompleted = true;
		notify();
	}

	public void run() {
		log.fine(StringRes.getString("controller.time.start"));
		mainLoop();
	}

	public TimeController(List<Player> turnOrder) {
		LogHelper.setDefaultOptions(log);
		this.turnOrder = turnOrder;
		nowPlaying = 0;
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
			
			nowPlaying++;  // update current player
			if (nowPlaying >= turnOrder.size()) {
				nowPlaying = 0;
			}
		}
	}
}
