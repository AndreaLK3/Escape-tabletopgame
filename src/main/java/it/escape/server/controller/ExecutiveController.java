package it.escape.server.controller;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

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
	
	protected static final Logger LOG = Logger.getLogger( ExecutiveController.class.getName() );
	
	private Player currentPlayer;
	
	private TimeController timeControllerRef;
	
	private TurnHandler turnHandler;
	
	private boolean runGame;
	
	private MapActionInterface map;
	
	public synchronized void startTurn(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		notify();
	}
	
	public synchronized void endGame() {
		runGame = false;
		notify();
	}

	public void run() {
		LOG.fine(StringRes.getString("controller.executor.start"));
		gameLoop();
		LOG.fine(StringRes.getString("controller.executor.finish"));
	}
	
	/** Note: adding "synchronize" in a code block where there is a wait() or notify() function is mandatory*/
	private synchronized void gameLoop() {
		while (runGame) {
			try {
				wait();  // wait to be awakened by startTurn() or endGame()
			} catch (InterruptedException e) {
				LOG.finer(StringRes.getString("controller.executor.awaken"));
			}
			if (!runGame) {  // was awaken by startTurn()
				gameTurn();
				timeControllerRef.endTurn();  // wake up timeController, prevents timeout
			}
		}
	}

	private void gameTurn() {

		if (currentPlayer instanceof Human) {
			turnHandler = new TurnHandlerHuman(currentPlayer, map);
		}
		else {
			turnHandler = new TurnHandlerAlien(currentPlayer, map);
		}
		turnHandler.executeTurnSequence();
	}


	public ExecutiveController(TimeController timeControllerRef, MapActionInterface map) {
		LogHelper.setDefaultOptions(LOG);
		this.map = map;
		this.timeControllerRef = timeControllerRef;
		runGame = true;		
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
