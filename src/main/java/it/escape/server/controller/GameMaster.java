package it.escape.server.controller;

public class GameMaster {
	
	private ExecutiveController executor;
	
	private TimeController timeController;
	
	private Thread executorThread;
	
	private Thread timerThread;

	public GameMaster() {
		timeController =  new TimeController();
		executor = new ExecutiveController(timeController);
		timeController.bindExecutor(executor);
		
		executorThread = new Thread(executor);
		timerThread = new Thread(timeController);
	}
	
	private void launchThreads() {
		executorThread.start();
		timerThread.start();
	}
}
