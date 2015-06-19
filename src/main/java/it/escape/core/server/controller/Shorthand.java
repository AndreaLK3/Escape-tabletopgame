package it.escape.core.server.controller;

import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.Announcer;
import it.escape.core.server.view.MessagingChannelInterface;

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
