package it.escape.server.controller;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.Announcer;
import it.escape.server.view.MessagingChannelInterface;

/**
 * Static class used to shadow long and unwieldy method chains
 * @author michele
 *
 */
public class Shorthand {
	
	public static Announcer announcer(MessagingChannelInterface interfaceWithUser) {
		return UserMessagesReporterSocket.getReporterInstance(interfaceWithUser).getAnnouncer();
	}
	
	public static Announcer announcer(PlayerActionInterface player) {
		return UserMessagesReporterSocket.getReporterInstance(player).getAnnouncer();
	}
}
