package it.escape.server.controller;

import static org.junit.Assert.*;

import java.util.List;

import it.escape.server.MapCreator;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.server.view.MessagingInterface;
import it.escape.strings.StringRes;

import org.junit.Test;

public class GameMasterTest {
	
	private final static int MAXPLAYERS = 8;

	@Test
	public void testNewPlayerTeamAssignation() {
		MapCreator stubMapCreator = new MapCreator("resources/Test_map.json");
		TestingAnnouncerObserver observer = new TestingAnnouncerObserver();
		GameMaster.setMapCreator(stubMapCreator);
		Announcer.getAnnouncerInstance().addObserver(observer);
		
		simulateConnect();
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 1, MAXPLAYERS),
				observer.getLast_message());
		simulateConnect();
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 2, MAXPLAYERS),
				observer.getLast_message());
		simulateConnect();
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 3, MAXPLAYERS),
				observer.getLast_message());
		simulateConnect();
		assertEquals(
				String.format(StringRes.getString("messaging.playerConnected"), 4, MAXPLAYERS),
				observer.getLast_message());
		
		List<Player> players = GameMaster.getInstanceByIndex(0).getPlayersList();
		assertEquals(players.get(0).getTeam(),PlayerTeams.ALIENS);
		assertEquals(players.get(1).getTeam(),PlayerTeams.HUMANS);
		assertEquals(players.get(2).getTeam(),PlayerTeams.ALIENS);
		assertEquals(players.get(3).getTeam(),PlayerTeams.HUMANS);
		
	}
	
	private void simulateConnect() {
		MessagingInterface iface = new MessagingInterface();
		UserMessagesReporter.createUMR(iface);
		GameMaster.newPlayerHasConnected(iface);
	}

}
