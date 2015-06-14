package it.escape.server;

import it.escape.server.controller.GameMaster;
import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannelInterface;
import it.escape.server.view.MessagingChannelStrings;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**Static class. It keeps a list of the GameMasters.
 * It receives 2 events:
 * 1) Connection of a new player, checking if a GameMaster has free spots and
 * invoking the proper functions inside the GameMaster so that thePlayer is added to the List. 
 * 2) Disconnection of a player, passing the handling request to the proper GameMaster
 * @author michele, andrea
 */
public class Master {
	
	private static final Logger LOG = Logger.getLogger( Master.class.getName() );
	
	private static List<GameMaster> gameMasters = new ArrayList<GameMaster>();
	private static int gmUniqueId = 0;
	private static GameMaster currentGameMaster = null;
	private static MapCreator mapCreator;
	private static ServerLocalSettings locals = null;
		
	public static void newPlayerHasConnected(MessagingChannelInterface interfaceWithUser, ServerLocalSettings Locals) {
		if (locals == null) {
			locals = Locals;
		}
		reaper();
		if (currentGameMaster == null) {
			LogHelper.setDefaultOptions(LOG);
			currentGameMaster = new GameMaster(mapCreator.getMap(), gmUniqueId, locals, new Announcer());
			LOG.info("Creating new gamemaster (id=" + gmUniqueId + ")");
			gmUniqueId++;
			gameMasters.add(currentGameMaster);
		}
		if (currentGameMaster.newPlayerAllowed()) {
			LOG.info("Routing user to existing gamemaster (id=" + (gmUniqueId-1) + ")");
			currentGameMaster.newPlayerMayCauseStart(interfaceWithUser);
		} else {
			currentGameMaster = new GameMaster(mapCreator.getMap(), gmUniqueId, locals, new Announcer());
			LOG.info("Creating new gamemaster (id=" + gmUniqueId + ")");
			gmUniqueId++;
			gameMasters.add(currentGameMaster);
			currentGameMaster.newPlayerMayCauseStart(interfaceWithUser);
		}
	}
	
	public static GameMaster getInstanceByIndex(int index) {
		return gameMasters.get(index);
	}
	
	/**
	 * When a player disconnects, the thread inside her Connection invokes this method.
	 * It finds the Master of her game, and tells it to handle this event.
	 * @param interfaceWithUser
	 */
	public static void playerHasDisconnected(MessagingChannelInterface interfaceWithUser) {
			
		Player offender = getPlayer(interfaceWithUser);
		GameMaster gm = getGameMasterOfPlayer(offender);
		if (gm != null) {
			gm.handlePlayerDisconnect(offender);
		}
	}
	
	public static Player getPlayer(MessagingChannelInterface interfaceWithUser) {
		return UserMessagesReporterSocket.getReporterInstance(interfaceWithUser).getThePlayer();
	}
	
	/**Given a Player, returns the GameMaster where the Player is in.
	 * Note: a Player is created when it is added to the GameMaster List, so the 
	 * function will surely return a GameMaster*/
	public static GameMaster getGameMasterOfPlayer (Player p) {
		for (GameMaster gm : gameMasters) {
			if (gm.hasPlayer(p) ){
				return gm;
			}
		}
			return null;
		}

	public static void setMapCreator(MapCreator creator) {
		mapCreator = creator;
	}

	/**
	 * Remove all the unused gamemasters
	 */
	private static void reaper() {
		List<GameMaster> temp = new ArrayList<GameMaster>(gameMasters);
		for (GameMaster gm : temp) {
			if (gm.isFinished()) {
				LOG.fine("Deleting old unused gamemaster");
				gameMasters.remove(gm);
				if (gm == currentGameMaster) {
					currentGameMaster = null;
				}
			}
		}
	}
	
	/**
	 * Order all the gamemasters to instantly stop the
	 * current game, disconnect all users, and terminate.
	 */
	public static void stopAll() {
		reaper();
		for (GameMaster gm : gameMasters) {
			gm.instaStopGame();
		}
	}
}
