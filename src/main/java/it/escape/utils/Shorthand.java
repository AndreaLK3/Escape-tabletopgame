package it.escape.utils;

import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingInterface;

/**
 * Static class used to shadow long and unwieldy method chains
 * @author michele
 *
 */
public class Shorthand {
	
	public static Announcer announcer(MessagingInterface interfaceWithUser) {
		return UserMessagesReporter.getReporterInstance(interfaceWithUser).getAnnouncer();
	}
	
	public static Announcer announcer(Player player) {
		return UserMessagesReporter.getReporterInstance(player).getAnnouncer();
	}
}
