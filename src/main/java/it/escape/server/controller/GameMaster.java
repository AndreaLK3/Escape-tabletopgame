package it.escape.server.controller;

import it.escape.server.MapCreator;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.server.view.MessagingInterface;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class GameMaster {
	
	protected static final Logger log = Logger.getLogger( GameMaster.class.getName() );
	
	private static List<GameMaster> gameMasters = new ArrayList<GameMaster>();
	private static GameMaster currentGameMaster = null;
	
	private static MapCreator mapCreator;
	
	private final static int MAXPLAYERS = 8;
	private int numPlayers = 0;
	
	private PlayerTeams currentTeam;
	
	private ExecutiveController executor;
	
	private TimeController timeController;
	
	private Thread executorThread;
	
	private Thread timerThread;
	
	private List<Player> listOfPlayers;
	
	private MapActionInterface map;
	
	public static void newPlayerHasConnected(MessagingInterface interfaceWithUser) {
		if (currentGameMaster == null) {
			LogHelper.setDefaultOptions(log);
			currentGameMaster = new GameMaster(mapCreator.getMap());
			gameMasters.add(currentGameMaster);
		}
		if (currentGameMaster.hasFreeSlots()) {
			currentGameMaster.addNewPlayer(interfaceWithUser);
		} else {
			currentGameMaster = new GameMaster(mapCreator.getMap());
			gameMasters.add(currentGameMaster);
			currentGameMaster.addNewPlayer(interfaceWithUser);
		}
	}
	
	public static void playerHasDisconnected(MessagingInterface interfaceWithUser) {
		for (GameMaster gm : gameMasters) {
			Player offender = UserMessagesReporter.getReporterInstance(interfaceWithUser).getThePlayer();
			if (gm.hasPlayer(offender)){
				gm.handlePlayerDisconnect(offender);
				break;
			}
		}
	}
	
	public static void setMapCreator(MapCreator creator) {
		mapCreator = creator;
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
		log.info(StringRes.getString("controller.gamemaster.startingGame"));
		launchThreads();
		waitForFinish();
		finalVictoryCheck();
		// final cleanup: close connections / update scoreboard / say goodbye
	}
	
	/* The interface is used to find the right UMR.*/
	public void addNewPlayer(MessagingInterface interfaceWithUser) {
		Player newP = createPlayer("default_Name");  // create the player
		listOfPlayers.add(newP);  // add him to our players list
		map.addNewPlayer(newP, newP.getTeam());  // tell the map to place our player
		UserMessagesReporter.bindPlayer(newP, interfaceWithUser);  // bind him to its command interface
		numPlayers++;  // update the player counter
		Announcer.getAnnouncerInstance().announcePlayerConnected(numPlayers,MAXPLAYERS);
	}
	
	private void handlePlayerDisconnect(Player player) {
		Announcer.getAnnouncerInstance().announcePlayerDisconnected(player);
		/*
		 * do some things:
		 * if the game has not yet started, simply remove him from the list
		 * if the game is already running, we may want to wait for him
		 * 		or maybe kill him off, or set him as idle/penalized
		 * what if an entire team disconnects a la MOBA?
		 * if the game has already ended, do nothing
		 */
	}
	
	/**
	 * used to check if there are places avaible for new players
	 */
	public boolean hasFreeSlots() {
		if (numPlayers < MAXPLAYERS) {
			return true;
		}
		return false;
	}
	
	private boolean hasPlayer(Player p) {
		if (listOfPlayers.contains(p)) {
			return true;
		}
		return false;
	}
	
	private Player createPlayer(String name) {
		Player newP = null;
		if (currentTeam == PlayerTeams.ALIENS) {	
			newP = new Alien(name);
			currentTeam = PlayerTeams.HUMANS;
			
		} else if (currentTeam == PlayerTeams.HUMANS) {
			newP = new Human(name);
			currentTeam = PlayerTeams.ALIENS;
		}	
		return newP;
	}
	
	private void launchThreads() {
		executorThread.start();
		timerThread.start();
	}
	
	private void waitForFinish() {
		try {
			timerThread.join();
		} catch (InterruptedException e) {
		}
		try {
			executorThread.join();
		} catch (InterruptedException e) {
		}
		log.info(StringRes.getString("controller.gamemaster.gameFinished"));
	}
	
	/**
	 * announce the winners
	 */
	private void finalVictoryCheck() {
		VictoryChecker conditions = new VictoryChecker(listOfPlayers);
		Announcer.getAnnouncerInstance().announceGameEnd();
		
		if (conditions.allHumansWin()) {
			Announcer.getAnnouncerInstance().announceTeamVictory(
					PlayerTeams.HUMANS,
					conditions.getHumanWinners());
			Announcer.getAnnouncerInstance().announceTeamDefeat(
					PlayerTeams.ALIENS);
		} else if (conditions.areThereHumanWinners()) {
			Announcer.getAnnouncerInstance().announceTeamVictory(
					PlayerTeams.HUMANS,
					conditions.getHumanWinners());
			Announcer.getAnnouncerInstance().announceTeamVictory(
					PlayerTeams.ALIENS,
					conditions.getAlienWinners());
		} else {
			Announcer.getAnnouncerInstance().announceTeamDefeat(
					PlayerTeams.HUMANS);
			Announcer.getAnnouncerInstance().announceTeamVictory(
					PlayerTeams.ALIENS,
					conditions.getAlienWinners());
		}
		
	}
}
