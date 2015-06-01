package it.escape.server.controller;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.Announcer;

/**
 * Static class used to shadow long and unwieldy method chains
 * @author michele
 *
 */
public class Shorthand {
	
	public static Announcer announcer(MessagingChannelInterface interfaceWithUser) {
		return UserMessagesReporter.getReporterInstance(interfaceWithUser).getAnnouncer();
	}
	
	public static Announcer announcer(PlayerActionInterface player) {
		return UserMessagesReporter.getReporterInstance(player).getAnnouncer();
	}
}
