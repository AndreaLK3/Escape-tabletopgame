package it.escape.server.controller;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.server.view.MessagingInterface;
import it.escape.strings.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**Responsibilities:
 * 1) If a new user connects:
 * 		a)bind his message interface to a Player
 * 		b)add him to the Player List of a GameMaster object
 * 		c)tell the GameMap to place a new Player
 * 		d)tell the Announcer to announce that a new Player has connected
 * 2) Handle the disconnection of a Player, depending on the current state of the game (started/in progress/finished).
 * 3) Keeping the List of Players.
 * 4) Initialize TimeController and ExecutiveController.
 * 5) Launch the threads to start a game.
 *n: The GameMaster class has a static list of the GameMaster objects.
 * When new users connect, and there is no GameMaster object with free spots, 
 * a new GameMaster object is created and added to the list.

 * When certain conditions are met, the game is started.
 * The game starting / life sequence is the following:
 *   1) Minimum number of players is reached: spawn subthread with timeout
 *   2) Maximum number of players reached: wake up subthread
 *   3) When the subthread is awaken, either by notify() or timeout,
 *      it will spawn the real worker threads, and wait for them to
 *      terminate successfully. This will mark the end of the game.
 *   4) Once the game is ended, victory/loss conditions will be assessed*/

public class GameMaster implements Runnable {

	protected static final Logger LOG = Logger.getLogger( GameMaster.class.getName() );
	
	private PlayerTeams currentTeam;
	
	private ExecutiveController executor;
	
	private TimeController timeController;
	
	private Thread executorThread;
	
	private Thread timerThread;
	
	private List<Player> listOfPlayers;
	
	private MapActionInterface map;
	
	private DecksHandler decksHandler;
	
	private Announcer announcer;
	
	VictoryChecker victoryChecker;
	
	private boolean gameRunning;

	private final static int WAIT_TIMEOUT = 60000;
	
	public final static int MAXPLAYERS = 8;
	public final static int MINPLAYERS = 2;
	private int numPlayers = 0;
	
	/** The constructor */
	public GameMaster(MapActionInterface map) {
		this.map = map;
		listOfPlayers = new ArrayList<Player>();
		timeController =  new TimeController(listOfPlayers);
		executor = new ExecutiveController(timeController, map);
		timeController.bindExecutor(executor);
		executorThread = new Thread(executor);
		timerThread = new Thread(timeController);
		currentTeam = PlayerTeams.ALIENS;
		announcer = new Announcer();
		gameRunning = false;
	}
	
	
	public synchronized void run() {
		LOG.fine(String.format(StringRes.getString("controller.gamemaster.gameStartTimeout"), WAIT_TIMEOUT/1000));
		// maybe we can announce it to the players, too
		try {
			wait(WAIT_TIMEOUT);
		} catch (InterruptedException e) {
		}
		startGameAndWait();
	}
	
	/**
	 * start the actual game, once ready.
	 * this function does not decide herself when we are ready
	 */
	public void startGameAndWait() {
		victoryChecker = new VictoryChecker(listOfPlayers);
		shufflePlayers();
		greetPlayers();
		gameRunning = true;
		LOG.info(StringRes.getString("controller.gamemaster.startingGame"));
		launchWorkerThreads();
		waitForFinish();
		LOG.info(StringRes.getString("controller.gamemaster.gameFinished"));
		finalVictoryCheck();
		// final cleanup: close connections / update scoreboard / say goodbye
	}
	
	
		
	/**
	 * Here's the logic to decide when to start the actual game
	 * @param interfaceWithUser
	 */
	public synchronized void newPlayerMayCauseStart(MessagingInterface interfaceWithUser) {
		addNewPlayer(interfaceWithUser);
		
		UserMessagesReporter.getReporterInstance(interfaceWithUser).relayMessage(String.format(
				StringRes.getString("messaging.serversMap"),
				map.getName()));
		
		announcer.announcePlayerConnected(numPlayers,GameMaster.MAXPLAYERS);  // the new user won't see this, as he hasn't yet subscribed
		
		if (numPlayers >= GameMaster.MINPLAYERS) {
			new Thread(this).start();
		} else if (numPlayers >= GameMaster.MAXPLAYERS) {
			notify();
		}
	}
	
	/* The interface is used to find the right UMR.*/
	private void addNewPlayer(MessagingInterface interfaceWithUser) {
		Player newP = createPlayer("default_Name");  // create the player
		listOfPlayers.add(newP);  // add him to our players list
		map.addNewPlayer(newP, newP.getTeam());  // tell the map to place our player
		UserMessagesReporter.bindPlayer(newP, interfaceWithUser);  // bind him to its command interface
		UserMessagesReporter.getReporterInstance(interfaceWithUser).bindAnnouncer(announcer);  // the player will also use our game-announcer
		numPlayers++;  // update the player counter
	}
	
	
	/**
	 * Handles a player's disconnection.
	 * Previously, the thread in the user's Connection has invoked the static method of GameMaster
	 * playerHasDisconnected, that in turn invokes this method.
	 * @param player
	 */
	public void handlePlayerDisconnect(Player player) {
		announcer.announcePlayerDisconnected(player);
		if (!isRunning()) {
			listOfPlayers.remove(player);
		}
		else {
			player.setUserIdle(true);
			if (getNumActivePlayers() < GameMaster.MINPLAYERS) {
				this.timeController.extraordinaryGameKill();
			}
			if (victoryChecker.entireTeamDisconnected()) {
				this.timeController.extraordinaryGameKill();
			}
		}
	
	}
	
	
	
	/** returns the number of players that are not currently idle / disconnected*/
	private int getNumActivePlayers() {
		int counter=0; 
		for (Player p: listOfPlayers)
			if (!p.isUserIdle())
				counter++;
			return counter;
	}
		
	
	/** returns true only if the game is accepting new players	 */
	public boolean newPlayerAllowed() {
		if (hasFreeSlots() && !isRunning()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Instantiate a new player object.
	 * Teams assignation is alternate, to maintain ingame balance
	 * @param name
	 * @return
	 */
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
	
	/**
	 * Randomize the player order
	 * (The list of players is ordered, and their respective
	 * turns will run in that order.)
	 */
	public void shufflePlayers() {
		int counter = listOfPlayers.size();
		Random randGen = new Random();
		Player temp;
		for (int i=0; i<counter; i++) {
			temp = listOfPlayers.remove(randGen.nextInt(counter)); 
			listOfPlayers.add(temp);
			}
	}
	
	/**
	 * Sends a greeting to the players
	 * Currently, the message does only tell them which team they're in
	 */
	private void greetPlayers() {
		for (Player p : listOfPlayers) {
			UserMessagesReporter.getReporterInstance(p).relayMessage(
					StringRes.getString("messaging.gamemaster.playAs") + " " + p.getTeam().toString());
			
		}
	}
	
	/** used to check if there the game managed by this gamemaster is running
	 * @return
	 */
	public boolean isRunning() {
		return gameRunning;
	}
	
	
	public boolean hasPlayer(Player p) {
		if (listOfPlayers.contains(p)) {
			return true;
		}
		return false;
	}
	
	/** used to check if there are places avaible for new players
	 */
	public boolean hasFreeSlots() {
		if (numPlayers < GameMaster.MAXPLAYERS) {
			return true;
		}
		return false;
	}
	

	/**used for testing / debugging purposes
	 * @return
	 */
	public List<Player> getPlayersList() {
		return listOfPlayers;
	}

	
	private void launchWorkerThreads() {
		executorThread.start();
		timerThread.start();
	}
	
	/**
	 * The game will end only when the worker threads terminate.
	 * The will stop on their own
	 */
	private void waitForFinish() {
		try {
			timerThread.join();
		} catch (InterruptedException e) {
		}
		try {
			executorThread.join();
		} catch (InterruptedException e) {
		}
		gameRunning = false;
	}
	
	/** announce the winners
	 */
	private void finalVictoryCheck() {
		announcer.announceGameEnd();
		
		if (victoryChecker.allHumansWin()) {
			announcer.announceTeamVictory(
					PlayerTeams.HUMANS,
					victoryChecker.getHumanWinners());
			announcer.announceTeamDefeat(
					PlayerTeams.ALIENS);
		} else if (victoryChecker.areThereHumanWinners()) {
			announcer.announceTeamVictory(
					PlayerTeams.HUMANS,
					victoryChecker.getHumanWinners());
			announcer.announceTeamVictory(
					PlayerTeams.ALIENS,
					victoryChecker.getAlienWinners());
		} else {
			announcer.announceTeamDefeat(
					PlayerTeams.HUMANS);
			announcer.announceTeamVictory(
					PlayerTeams.ALIENS,
					victoryChecker.getAlienWinners());
		}
	}
	
	
	
}
