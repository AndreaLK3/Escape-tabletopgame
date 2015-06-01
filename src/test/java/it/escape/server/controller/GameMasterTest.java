package it.escape.server.controller;

import static org.junit.Assert.assertEquals;
import it.escape.server.MapCreator;
import it.escape.server.Master;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.server.view.MessagingChannel;
import it.escape.strings.StringRes;

import java.util.List;

import org.junit.Test;

public class GameMasterTest {
	
	private final static int MAXPLAYERS = 8;

	@Test
	public void testNewPlayerTeamAssignation() {
		MapCreator stubMapCreator = new MapCreator("resources/Test_map.json");
		TestingAnnouncerObserver observer = new TestingAnnouncerObserver();
		Master.setMapCreator(stubMapCreator);
		
		simulateConnect(observer);
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
	
	private void simulateConnect(TestingAnnouncerObserver observer) {
		MessagingChannel iface = new MessagingChannel();
		UserMessagesReporter.createUMR(iface);
		Master.newPlayerHasConnected(iface);
		Shorthand.announcer(iface).addObserver(observer);
	}

}
