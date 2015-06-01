package it.escape.server;

import it.escape.server.controller.GameMaster;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.game.actions.MapActionInterface;
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
import java.util.logging.Logger;

/**Static class. It keeps a list of the GameMasters.
 * It receives 2 events:
 * 1) Connection of a new player, checking if a GameMaster has free spots and
 * invoking the proper functions inside the GameMaster so that thePlayer is added to the List. 
 * 2) Disconnection of a player, passing the handling request to the proper GameMaster
 * @author michele, andrea
 */
public class Master {
	
	protected static final Logger LOG = Logger.getLogger( Master.class.getName() );
	
	private static List<GameMaster> gameMasters = new ArrayList<GameMaster>();
	private static GameMaster currentGameMaster = null;
	private static MapCreator mapCreator;
	
		
	public static void newPlayerHasConnected(MessagingChannel interfaceWithUser) {
		if (currentGameMaster == null) {
			LogHelper.setDefaultOptions(LOG);
			currentGameMaster = new GameMaster(mapCreator.getMap());
			gameMasters.add(currentGameMaster);
		}
		if (currentGameMaster.newPlayerAllowed()) {
			currentGameMaster.newPlayerMayCauseStart(interfaceWithUser);
		} else {
			currentGameMaster = new GameMaster(mapCreator.getMap());
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
	public static void playerHasDisconnected(MessagingChannel interfaceWithUser) {
			
		Player offender = getPlayer(interfaceWithUser);
		GameMaster gm = getGameMasterOfPlayer(offender);
		gm.handlePlayerDisconnect(offender);		
	}
	
	public static Player getPlayer(MessagingChannel interfaceWithUser) {
		return UserMessagesReporter.getReporterInstance(interfaceWithUser).getThePlayer();
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


	
}
