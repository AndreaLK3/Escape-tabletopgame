package it.escape.server.controller;

import static org.junit.Assert.assertEquals;
import it.escape.core.server.MapCreator;
import it.escape.core.server.Master;
import it.escape.core.server.controller.GameMaster;
import it.escape.core.server.controller.Shorthand;
import it.escape.core.server.controller.UserMessagesReporterSocket;
import it.escape.core.server.model.AnnouncerStrings;
import it.escape.core.server.model.SuperAnnouncer;
import it.escape.core.server.model.game.players.Player;
import it.escape.core.server.model.game.players.PlayerTeams;
import it.escape.core.server.view.MessagingChannelStrings;
import it.escape.server.controller.AnnouncerObserverTest;
import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;

import java.util.List;

import org.junit.Test;

public class GameMasterTest {
	
	private final static int MAXPLAYERS = GameMaster.MAXPLAYERS;
	/**
	 * Simulate the connection of new players, intercepting
	 * the "new player has connected" message, and considering
	 * the connection successful if said message is generated
	 */
	@Test
	public void testNewPlayerTeamAssignation() {
		String[] maps = {"Galilei"};
		MapCreator stubMapCreator = new MapCreator(maps);
		AnnouncerObserverTest observer = new AnnouncerObserverTest();
		Master.setMapCreator(stubMapCreator);
		
		simulateConnect(observer); // the message isn't generated because the server *was* empty
		simulateConnect(observer);
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 2, MAXPLAYERS),
				observer.getLast_message());
		simulateConnect(observer);
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 3, MAXPLAYERS),
				observer.getLast_message());
		simulateConnect(observer);
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 4, MAXPLAYERS),
				observer.getLast_message());
		
		List<Player> players = Master.getInstanceByIndex(0).getPlayersList();
		assertEquals(players.get(0).getTeam(),PlayerTeams.ALIENS);
		assertEquals(players.get(1).getTeam(),PlayerTeams.HUMANS);
		assertEquals(players.get(2).getTeam(),PlayerTeams.ALIENS);
		assertEquals(players.get(3).getTeam(),PlayerTeams.HUMANS);
		
	}
	
	private void simulateConnect(AnnouncerObserverTest observer) {
		MessagingChannelStrings iface = new MessagingChannelStrings();
		UserMessagesReporterSocket.createUMR(iface);
		Master.newPlayerHasConnected(iface, new GlobalSettings());
		SuperAnnouncer superAnnouncer = (SuperAnnouncer)Shorthand.announcer(iface);
		AnnouncerStrings announcer = superAnnouncer.getSockAnnouncer();
		announcer.addObserver(observer);
	}

}
