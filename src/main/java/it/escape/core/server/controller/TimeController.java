package it.escape.core.server.controller;

import it.escape.core.server.model.game.players.Player;
import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.LogHelper;

import java.util.List;
import java.util.logging.Level;
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
	
	protected static final Logger LOGGER = Logger.getLogger( TimeController.class.getName() );
	
	private final int TIMEOUT;
	
	private final static int SYNCHRO_SCAN_TIME = 10;
	
	private final static int MAX_TURNS = 39;
	
	private ExecutiveController executorRef;
	
	private boolean runGame;
	
	private boolean turnCompleted;
	
	private final GlobalSettings locals;
	
	private List<Player> turnOrder; // reference to the *ordered* list of players
	private int nowPlaying;
	
	private int turnNumber;
	
	public synchronized void endTurn() {
		turnCompleted = true;
		notify();
	}

	public void run() {
		LOGGER.fine(StringRes.getString("controller.time.start"));
		turnNumber = 1;
		synchroStart();
		mainLoop();
		LOGGER.fine(StringRes.getString("controller.time.finish"));
	}
	
	/**
	 * Stop the program flow until the executor goes into
	 * sleeping mode (ready to receive startTurn).
	 * It's way less convoluted to do it this way that to use
	 * multiple locks
	 */
	private void synchroStart() {
		boolean asleep = executorRef.isAsleep();
		while (!asleep) {
			try {
				Thread.sleep(SYNCHRO_SCAN_TIME);
			} catch (InterruptedException e) {
				LOGGER.log(Level.FINEST, "unexpected interruption", e);
			}
			asleep = executorRef.isAsleep();
		}
	}

	public TimeController(List<Player> turnOrder, GlobalSettings locals) {
		LogHelper.setDefaultOptions(LOGGER);
		this.locals = locals;
		TIMEOUT = this.locals.getGameTurnDuration();
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
			LOGGER.fine("Issuing startTurn command");
			executorRef.startTurn(current);  // run current player's turn
			
			try {
				wait(TIMEOUT);  // wait for it to end
			} catch (InterruptedException e) {
				LOGGER.log(Level.FINEST, "unexpected interruption", e);
			}
			LOGGER.finer("Awaken");
			
			if (!turnCompleted) {  // didn't end in time? End it for him
				LOGGER.fine(StringRes.getString("controller.time.forcingDefaults"));
				executorRef.fillInDefaultChoices();
				
				try {
					/*
					 * the executiveController will now conclude the turn
					 * properly, and notify our timeController; we catch the
					 * notify and go on as usual.
					 * The timeout will prevent the thread from stalling if
					 * executor fires its notify "too soon"
					 */
					wait();
				} catch (InterruptedException e) {
					LOGGER.log(Level.FINEST, "unexpected interruption", e);
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
