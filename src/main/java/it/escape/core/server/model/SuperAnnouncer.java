package it.escape.core.server.model;

import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.game.cards.ObjectCard;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.core.server.model.game.players.Player;
import it.escape.core.server.model.game.players.PlayerTeams;

import java.util.List;

/**
 * This class will aggregate the RMI and Strings Announcers,
 * It exposes methods for creating / getting them,
 * And will forward the announce to both.
 * @author michele, andrea
 *
 */
public class SuperAnnouncer implements Announcer {

	private AnnouncerStrings socketAnnouncer = null;

	private AnnouncerRMIBroadcast rmiAnnouncer = null;

	public SuperAnnouncer() {

	}

	public void createSockAnnouncerIfNeeded() {
		if (socketAnnouncer == null) {
			socketAnnouncer = new AnnouncerStrings();
		}
	}

	public void createRMIAnnouncerIfNeeded() {
		if (rmiAnnouncer == null) {
			rmiAnnouncer = new AnnouncerRMIBroadcast();
		}
	}

	public AnnouncerStrings getSockAnnouncer() {
		return socketAnnouncer;
	}

	public AnnouncerRMIBroadcast getRMIAnnouncer() {
		return rmiAnnouncer;
	}

	@Override
	public void announcePlayerConnected(int connected, int maximum) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announcePlayerConnected(connected, maximum);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announcePlayerConnected(connected, maximum);
		}
	}

	@Override
	public void announcePlayerDisconnected(PlayerActionInterface player) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announcePlayerDisconnected(player);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announcePlayerDisconnected(player);
		}
	}

	@Override
	public void announceAttack(PlayerActionInterface player,
			PositionCubic position) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceAttack(player, position);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceAttack(player, position);
		}
	}

	@Override
	public void announceNoise(String location) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceNoise(location);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceNoise(location);
		}
	}

	@Override
	public void announceObjectCard(PlayerActionInterface player,
			ObjectCard theCard) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceObjectCard(player, theCard);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceObjectCard(player, theCard);
		}
	}

	@Override
	public void announceDeath(PlayerActionInterface victim) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceDeath(victim);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceDeath(victim);
		}
	}

	@Override
	public void announceDefense(PositionCubic position) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceDefense(position);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceDefense(position);
		}
	}

	@Override
	public void announce(String message) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announce(message);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announce(message);
		}
	}

	@Override
	public void announcePlayerPosition(PlayerActionInterface p,
			PositionCubic position) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announcePlayerPosition(p, position);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announcePlayerPosition(p, position);
		}
	}

	@Override
	public void announceEscape(PlayerActionInterface currentPlayer) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceEscape(currentPlayer);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceEscape(currentPlayer);
		}
	}

	@Override
	public void announceGameEnd() {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceGameEnd();
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceGameEnd();
		}
	}

	@Override
	public void announceEndOfResults() {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceEndOfResults();
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceEndOfResults();
		}
	}

	@Override
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceTeamVictory(team, members);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceTeamVictory(team, members);
		}
	}

	@Override
	public void announceTeamDefeat(PlayerTeams team) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceTeamDefeat(team);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceTeamDefeat(team);
		}
	}

	@Override
	public void announcePlayerRename(String oldname, String newname) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announcePlayerRename(oldname, newname);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announcePlayerRename(oldname, newname);
		}
	}

	@Override
	public void announceChatMessage(PlayerActionInterface player, String message) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceChatMessage(player, message);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceChatMessage(player, message);
		}
	}

	@Override
	public void announceGameStartETA(int seconds) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceGameStartETA(seconds);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceGameStartETA(seconds);
		}
	}

	@Override
	public void announceNewTurn(int turnNumber, String playerName) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceNewTurn(turnNumber, playerName);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceNewTurn(turnNumber, playerName);
		}
	}

	@Override
	public void announceEscapePodUnavailable(String position) {
		if (rmiAnnouncer != null) {
			rmiAnnouncer.announceEscapePodUnavailable(position);
		}
		if (socketAnnouncer != null) {
			socketAnnouncer.announceEscapePodUnavailable(position);
		}
	}

}
