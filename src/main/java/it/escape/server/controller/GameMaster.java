package it.escape.server.controller;

import it.escape.GlobalSettings;
import it.escape.server.ServerLocalSettings;
import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.server.view.MessagingChannel;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
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

	private static final Logger LOG = Logger.getLogger( GameMaster.class.getName() );
	
	private final int id; 
	
	private PlayerTeams currentTeam;
	
	private ExecutiveController executor;
	
	private TimeController timeController;
	
	private Thread executorThread;
	
	private Thread timerThread;
	
	private List<Player> listOfPlayers;
	
	private List<AsyncUserListener> listeners;
	
	private MapActionInterface map;
	
	private DecksHandler decksHandler;
	
	private Announcer announcer;
	
	VictoryChecker victoryChecker;
	
	private boolean gameRunning;  // true if the game is taking place
	
	private boolean gameFinished;  // true if the game has concluded normally
	
	private AtomicBoolean timeoutTicking;  // true if the starting timeout is up andd running
	
	private Thread ownThread = null;
	
	private final ServerLocalSettings locals;

	private final int WAIT_TIMEOUT;
	
	private int started_countdown;
	
	private final static int USERID_RANDOMIZE = 10000;
	
	public final static int MAXPLAYERS = 8;
	public final static int MINPLAYERS = 2;
	private int numPlayers = 0;
	
	/** The constructor */
	public GameMaster(MapActionInterface map, int id, ServerLocalSettings locals) {
		LogHelper.setDefaultOptions(LOG);
		this.id = id;
		this.map = map;
		this.locals = locals;
		WAIT_TIMEOUT = this.locals.getGameMasterTimeout();
		decksHandler = new DecksHandler();
		announcer = new Announcer();
		listOfPlayers = new ArrayList<Player>();
		listeners = new ArrayList<AsyncUserListener>();
		timeController =  new TimeController(listOfPlayers);
		executor = new ExecutiveController(timeController, map, decksHandler);
		timeController.bindExecutor(executor);
		executorThread = new Thread(executor);
		timerThread = new Thread(timeController);
		currentTeam = PlayerTeams.ALIENS;
		gameRunning = false;
		gameFinished = false;
		timeoutTicking = new AtomicBoolean(false);
	}
	
	
	public synchronized void run() {
		timeoutTicking.set(true);
		started_countdown = (int) System.currentTimeMillis();
		LOG.fine(String.format(StringRes.getString("controller.gamemaster.gameStartTimeout"), WAIT_TIMEOUT/1000));
		announcer.announceGameStartETA(WAIT_TIMEOUT / 1000);
		try {
			wait(WAIT_TIMEOUT);
		} catch (InterruptedException e) {
		}
		if (numPlayers >= GameMaster.MINPLAYERS) {  // someone disconnected in the meantime? no? good.
			timeoutTicking.set(false);
			startGameAndWait();
			LOG.fine("GameMaster tasks completed, thread will now stop");
		} else {
			// TODO: logging / inform users
			ownThread = null;  // one last thing: erase the reference, so that the thread will be dead for good
			timeoutTicking.set(false);
		}
		
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
		// TODO: if we had a persistent scoreboard, here's where we would update it
		closeConnections();
	}
	
	/**
	 * Invoked by Master, sequence of action to perform when a new user connects
	 * @param interfaceWithUser
	 */
	public void newPlayerMayCauseStart(MessagingChannel interfaceWithUser) {
		addNewPlayer(interfaceWithUser);
		announceNewPlayer(interfaceWithUser);
		gameStartLogic();
	}
	
	/* The interface is used to find the right UMR.*/
	private void addNewPlayer(MessagingChannel interfaceWithUser) {
		Player newP = createPlayer("Guest-" + id + "-" + new Random().nextInt(USERID_RANDOMIZE));  // create the player
		map.addNewPlayer(newP, newP.getTeam());  // tell the map to place our player
		UserMessagesReporter.bindPlayer(newP, interfaceWithUser);  // bind him to its command interface
		UserMessagesReporter.getReporterInstance(interfaceWithUser).bindAnnouncer(announcer);  // the player will also use our game-announcer
		AsyncUserListener listener = new AsyncUserListener(newP, announcer, UserMessagesReporter.getReporterInstance(interfaceWithUser), this);
		listOfPlayers.add(newP);  // add him to our players list
		interfaceWithUser.addObserver(listener);
		listeners.add(listener);
		numPlayers++;  // update the player counter
	}
	
	private void announceNewPlayer(MessagingChannel interfaceWithUser) {
		UserMessagesReporter.getReporterInstance(interfaceWithUser).relayMessage(String.format(
				StringRes.getString("messaging.serversMap"),
				map.getName()));  // greet him
		
		announcer.announcePlayerConnected(numPlayers,GameMaster.MAXPLAYERS);  // notify the others
		UserMessagesReporter.getReporterInstance(interfaceWithUser).relayMessage(String.format(
				StringRes.getString("messaging.othersWaiting"),
				numPlayers,
				GameMaster.MAXPLAYERS));  // tell him how many players are connected
		if (timeoutTicking.get()) {  // if a game is about to start
			UserMessagesReporter.getReporterInstance(interfaceWithUser).relayMessage(String.format(
					StringRes.getString("messaging.gameStartETA"),
					getStartGameETA()));  // tell him how long until game starts
		}
	}
	/**
	 * Here's the logic to decide when to start the actual game
	 * @param interfaceWithUser
	 */
	private synchronized void gameStartLogic() {
		if (numPlayers >= GameMaster.MINPLAYERS) {
			if (!timeoutTicking.get()) {  // start the subthread only if necessary
				ownThread = new Thread(this);
				ownThread.start();
			}
		} else if (numPlayers >= GameMaster.MAXPLAYERS) {
			notify();
		}
	}
	
	/**
	 * Handles a player's disconnection.
	 * Previously, the thread in the user's Connection has invoked the static method of GameMaster
	 * playerHasDisconnected, that in turn invokes this method.
	 * @param player
	 */
	public void handlePlayerDisconnect(Player player) {
		if (!isFinished()) {
			announcer.announcePlayerDisconnected(player);
			if (!isRunning()) {
				LOG.info("Player disconnected while the game was not up");
				listOfPlayers.remove(player);
				numPlayers--;
			}
			else {
				player.setUserIdle(true);
				if (getNumActivePlayers() < GameMaster.MINPLAYERS) {
					LOG.info("Too few players left. Terminating game.");
					this.timeController.extraordinaryGameKill();
				} else if (victoryChecker.entireTeamDisconnected()) {
					LOG.info("Team disconnected. Terminating game.");
					this.timeController.extraordinaryGameKill();
				}
			}
		}
	}
	
	/**
	 * Instantly terminate the running game.
	 * Invoked by Master
	 */
	public void instaStopGame() {
		if (isRunning()) {
			LOG.info("Terminating game now!");
			this.timeController.extraordinaryGameKill();
			try {
				ownThread.join();  // wait for the thread to actually terminate
			} catch (InterruptedException e) {
			}
			LOG.fine("Game terminated.");
		} else {
			LOG.info("No running game to terminate");
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
		if (hasFreeSlots() && !isRunning() && !isFinished()) {
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
	 * Sends a greeting to the players (When the game starts, not when they connect)
	 * Currently, the message does only tell them which team they're in
	 */
	private void greetPlayers() {
		for (Player p : listOfPlayers) {
			UserMessagesReporter.getReporterInstance(p).relayMessage(String.format(
					StringRes.getString("messaging.gamemaster.playAs"),
					p.getTeam().toString()));
		}
	}
	
	private int getStartGameETA() {
		int passed = (int) System.currentTimeMillis() - started_countdown;
		return (WAIT_TIMEOUT/1000) - (passed/1000);
	}
	
	/** used to check if there the game managed by this gamemaster is running
	 * @return
	 */
	public boolean isRunning() {
		return gameRunning;
	}
	
	/**
	 * Used by Master to check if the GameMaster has finished its work
	 * @return
	 */
	public boolean isFinished() {
		return gameFinished;
	}
	
	/**
	 * Used to check if a specific player belongs to this game
	 * @param p
	 * @return
	 */
	public boolean hasPlayer(Player p) {
		if (listOfPlayers.contains(p)) {
			return true;
		}
		return false;
	}
	
	public boolean hasPlayerNamed(String name) {
		for (Player p : listOfPlayers) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public String getPlayerPosition(PlayerActionInterface p) {
		return map.getPlayerAlphaNumPosition(p);
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
	 * Once this is done, set the game state booleans
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
		gameFinished = true;
	}
	
	/** announce the winners
	 */
	private void finalVictoryCheck() {
		VictoryAnnounce vic = new VictoryAnnounce(announcer, victoryChecker);
		announcer.announceGameEnd();
		
		if (victoryChecker.allHumansDisconnected()) {
			vic.totalAlienWin();
		} else if (victoryChecker.allAliensDisconnected()) {
			vic.totalHumanWin();
		} else if (victoryChecker.allHumansWin()) {
			vic.totalHumanWin();
		} else if (victoryChecker.areThereHumanWinners()) {
			vic.partialHumanWin();
		} else {
			vic.totalAlienWin();
		}
		
		announcer.announceEndOfResults();
	}
	
	private synchronized void closeConnections() {
		for (Player p : listOfPlayers) {
			UserMessagesReporter.getReporterInstance(p).relayMessage(String.format(
					StringRes.getString("messaging.goodbye"),
					p.getName()));  // say goodbye
			UserMessagesReporter.getReporterInstance(p).getInterfaceWithUser().killConnection();
			numPlayers--;
		}
	}
	
}
