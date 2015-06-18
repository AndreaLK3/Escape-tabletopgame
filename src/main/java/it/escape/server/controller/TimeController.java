package it.escape.server.controller;

import it.escape.server.ServerLocalSettings;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.List;
import java.util.logging.Logger;

/**
 * This class will organize the timing of a single active game:
 * the turn succession, the timing, the game ending.
 * It won't, however, manage the specific choreography of
 * the turn.
 * 
 * This class will always exist as an object occupying its own thread.
 * This object is meant to cooperate with ExecutiveController
 * 
 * @author michele
 *
 */
public class TimeController implements Runnable {
	
	protected static final Logger log = Logger.getLogger( TimeController.class.getName() );
	
	private final int TIMEOUT;  // milliseconds
	
	private final static int MAX_TURNS = 40;
	
	private ExecutiveController executorRef;
	
	private boolean runGame;
	
	private boolean turnCompleted;
	
	private final ServerLocalSettings locals;
	
	private List<Player> turnOrder; // reference to the *ordered* list of players
	private int nowPlaying;
	
	private int turnNumber;
	
	public synchronized void endTurn() {
		turnCompleted = true;
		notify();
	}

	public void run() {
		log.fine(StringRes.getString("controller.time.start"));
		turnNumber = 1;
		mainLoop();
		log.fine(StringRes.getString("controller.time.finish"));
	}

	public TimeController(List<Player> turnOrder, ServerLocalSettings locals) {
		LogHelper.setDefaultOptions(log);
		this.locals = locals;
		TIMEOUT = locals.getGameTurnDuration();
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
			Shorthand.announcer(current).announceNewTurn(turnNumber,current.getName());
			log.fine("Issuing startTurn command");
			executorRef.startTurn(current);  // run current player's turn
			
			try {
				wait(TIMEOUT);  // wait for it to end
			} catch (InterruptedException e) {
			}
			log.finer("Awaken");
			
			if (!turnCompleted) {  // didn't end in time? End it for him
				log.fine(StringRes.getString("controller.time.forcingDefaults"));
				executorRef.fillInDefaultChoices();
				
				try {
					/*
					 * the executiveController will now conclude the turn
					 * properly, and notify our timeController; we catch the
					 * notify and go on as usual.
					 * The timeout will prevent the thread from stalling if
					 * executor fires its notify "too soon"
					 */
					wait(100);
				} catch (InterruptedException e) {
				}
			}
			if (!runGame) {
				endGame();
			}
			intermediateVictoryCheck();  // game might terminate here
			nowPlaying++;  // update current player
			if (nowPlaying >= turnOrder.size()) {
				nowPlaying = 0;
				turnNumber++;  // update current turn
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
	
	/**
	 * Intended to be used by GameMaster, who can and *will* abruptly terminate the game if certain conditions are met.
	 * Please note that, even in this case, fillInDefaultChoices() will be called to un-block the executor thread.
	 * Both that and our thread must terminate for a game to end correctly.
	 */
	public synchronized void extraordinaryGameKill() {
		runGame = false;
		notify();
	}
}
