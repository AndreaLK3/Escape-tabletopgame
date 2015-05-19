package it.escape.server.controller;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingInterface;

import java.util.ArrayList;
import java.util.List;


public class GameMaster {
	
	private final static int MAXPLAYERS = 8;
	private PlayerTeams currentTeam;
	
	private ExecutiveController executor;
	
	private TimeController timeController;
	
	private Thread executorThread;
	
	private Thread timerThread;
	
	private List<Player> listOfPlayers;

	public GameMaster() {
		timeController =  new TimeController();
		executor = new ExecutiveController(timeController);
		timeController.bindExecutor(executor);
		executorThread = new Thread(executor);
		timerThread = new Thread(timeController);
		listOfPlayers = new ArrayList<Player>();
		currentTeam = PlayerTeams.ALIENS;
	}
	
	/* The interface is used to find the right UMR.*/
	public void newPlayerHasConnected(MessagingInterface interfaceWithUser) {
		Player newP = createPlayer();
		listOfPlayers.add(newP);
		UserMessagesReporter.bindPlayer(newP, interfaceWithUser);
	}
	
	private Player createPlayer() {
		return null;
	}
	
	private void launchThreads() {
		executorThread.start();
		timerThread.start();
	}
}
