package it.escape.server.controller;

import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.logging.Logger;

public class TimeController implements Runnable {
	
	protected static final Logger log = Logger.getLogger( TimeController.class.getName() );
	
	private final Integer TIMEOUT = 60000;  // milliseconds
	
	private ExecutiveController executorRef;
	
	private boolean runGame;
	
	private boolean turnCompleted;
	
	// lista ordinata dei player
	// indice player corrente
	
	public synchronized void endTurn() {
		turnCompleted = true;
		notify();
	}

	public void run() {
		log.fine(StringRes.getString("controller.time.start"));
		mainLoop();
	}

	public TimeController() {
		LogHelper.setDefaultOptions(log);
		this.runGame = true;
	}

	public void bindExecutor(ExecutiveController executorRef) {
		this.executorRef = executorRef;
	}
	
	private void mainLoop() {
		while (runGame) {
			turnCompleted = false;
			// get player corrente
			// risveglia executor
			// wait timeout
			
			// turno finito male? dico all'executor: completa le opzioni default
			
			// passa al player successivo
		}
	}
}
