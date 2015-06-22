package it.escape.core.server.controller;

import it.escape.core.server.controller.game.actions.DecksHandlerInterface;
import it.escape.core.server.controller.game.actions.MapActionInterface;
import it.escape.core.server.model.game.players.Human;
import it.escape.core.server.model.game.players.Player;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.LogHelper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * This class will host the actual turnHandlers, in which the
 * turn logic is executed.
 * 
 * This class is intended to live as an object occupying its own thread,
 * turnHandler will also live in this thread.
 * This object is meant to cooperate with TimeController
 * 
 * @author michele
 *
 */
public class ExecutiveController implements Runnable {
	
	protected static final Logger LOGGER = Logger.getLogger( ExecutiveController.class.getName() );
	
	private Player currentPlayer;
	
	private TimeController timeControllerRef;
	
	private TurnHandler turnHandler;
	
	private boolean runGame;
	
	private AtomicBoolean sleeping;
	
	private MapActionInterface map;
	
	private DecksHandlerInterface deck;
	
	/**This function is invoked by TimeController's thread; it awakens the
	 * Executor's thread that was in the method gameLoop() inside this class
	 * @param currentPlayer 
	 */
	public synchronized void startTurn(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		notify();
	}
	
	public synchronized void endGame() {
		runGame = false;
		notify();
	}

	public void run() {
		LOGGER.fine(StringRes.getString("controller.executor.start"));
		gameLoop();
		LOGGER.fine(StringRes.getString("controller.executor.finish"));
	}
	
	/**This method is executed by executorThread. It has a wait(), 
	 * and it is awakened by 2 functions invoked by timeController thread: startTurn(), or endGame() 
	 * Note: adding "synchronize" in a code block where there is a wait() or notify() function is mandatory*/
	private synchronized void gameLoop() {
		while (runGame) {
			try {
				sleeping.set(true);
				LOGGER.finer("sleeping");
				wait();  // wait to be awakened by startTurn() or endGame()
			} catch (InterruptedException e) {
			}
			sleeping.set(false);
			LOGGER.finer(StringRes.getString("controller.executor.awaken"));
			if (runGame) {  // was awaken by startTurn()
				gameTurn();
				timeControllerRef.endTurn();  // wake up timeController, prevents timeout
			}
		}
	}
	
	public synchronized boolean isAsleep() {
		return sleeping.get();
	}

	private void gameTurn() {

		if (currentPlayer instanceof Human) {
			turnHandler = new TurnHandlerHuman(currentPlayer, map, deck);
		}
		else {
			turnHandler = new TurnHandlerAlien(currentPlayer, map, deck);
		}
		LOGGER.fine("Executing turn sequence");
		turnHandler.executeTurnSequence();
		LOGGER.fine("Turn sequence completed");
	}


	public ExecutiveController(TimeController timeControllerRef, MapActionInterface map, DecksHandlerInterface deck) {
		LogHelper.setDefaultOptions(LOGGER);
		this.map = map;
		this.deck = deck;
		this.timeControllerRef = timeControllerRef;
		runGame = true;
		sleeping = new AtomicBoolean(false);
	}
	
	/**
	 * invoked by TimeController;
	 * automatically complete the actions for this turn
	 * at this point gameTurn() will return on its own
	 */
	public void fillInDefaultChoices() {
		turnHandler.fillInDefaultChoices();  // propagate the call
	}
}
