package it.escape.server.controller;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.server.view.MessagingInterface;

import java.util.ArrayList;
import java.util.List;


public class GameMaster {
	
	private static List<GameMaster> gameMasters = new ArrayList<GameMaster>();
	private static GameMaster currentGameMaster = null;
	
	private final static int MAXPLAYERS = 8;
	private int numPlayers = 0;
	
	private PlayerTeams currentTeam;
	
	private ExecutiveController executor;
	
	private TimeController timeController;
	
	private Thread executorThread;
	
	private Thread timerThread;
	
	private List<Player> listOfPlayers;
	
	private MapActionInterface map;
	
	public static void newPlayerHasConnected(MessagingInterface interfaceWithUse) {
		if (currentGameMaster == null) {
			currentGameMaster = new GameMaster(map);
		}
	}

	private GameMaster(MapActionInterface map) {
		this.map = map;
		timeController =  new TimeController(listOfPlayers);
		executor = new ExecutiveController(timeController, map);
		timeController.bindExecutor(executor);
		executorThread = new Thread(executor);
		timerThread = new Thread(timeController);
		listOfPlayers = new ArrayList<Player>();
		currentTeam = PlayerTeams.ALIENS;
		
	}
	
	/**
	 * start the actual game, once ready.
	 * this function does not decide herself when we are ready
	 */
	public void startGame() {
		launchThreads();
	}
	
	/* The interface is used to find the right UMR.*/
	public void addNewPlayer(MessagingInterface interfaceWithUser) {
		Player newP = createPlayer();  // create the player
		listOfPlayers.add(newP);  // add him to our players list
		map.addNewPlayer(newP, newP.getTeam());  // tell the map to place our player
		UserMessagesReporter.bindPlayer(newP, interfaceWithUser);  // bind him to its command interface
		numPlayers++;  // update the player counter
	}
	
	/**
	 * used by the external program logic to check if there are
	 * places avaible for new players
	 */
	public boolean hasFreeSlots() {
		if (numPlayers < MAXPLAYERS) {
			return true;
		}
		return false;
	}
	
	private Player createPlayer() {
		Player newP= null;
		if (currentTeam == PlayerTeams.ALIENS) {	
			newP = new Alien();
			currentTeam = PlayerTeams.HUMANS;
			
		} else if (currentTeam == PlayerTeams.HUMANS) {
			newP = new Human();
			currentTeam = PlayerTeams.ALIENS;
		}	
		return newP;
	}
	
	private void launchThreads() {
		executorThread.start();
		timerThread.start();
	}
}
