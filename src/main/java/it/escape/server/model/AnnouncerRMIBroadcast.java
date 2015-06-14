package it.escape.server.model;

import it.escape.client.connection.rmi.ClientRemoteInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;

import java.util.List;

/**
 * Announcer based on the RMI system, instead of being an observable
 * carrying a message string, it directly invokes specific
 * remote methods in its subscribers
 * @author michele
 *
 */
public class AnnouncerRMIBroadcast implements Announcer {
	
	private List<ClientRemoteInterface> subscribed;
	
	public AnnouncerRMIBroadcast() {
		
	}
	
	public void subscribe(ClientRemoteInterface client) {
		subscribed.add(client);
	}
	
	public void unSubscribe(ClientRemoteInterface client) {
		subscribed.remove(client);
	}

	@Override
	public void announcePlayerConnected(int connected, int maximum) {
		for (ClientRemoteInterface client : subscribed) {
			client.playerConnected(connected, maximum);
		}
	}

	@Override
	public void announcePlayerDisconnected(PlayerActionInterface player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceAttack(PlayerActionInterface player,
			PositionCubic position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceNoise(String location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceObjectCard(PlayerActionInterface player,
			ObjectCard theCard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceDeath(PlayerActionInterface victim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceDefense(PositionCubic position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announce(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announcePlayerPosition(PlayerActionInterface p,
			PositionCubic position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceEscape(PlayerActionInterface currentPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceGameEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceEndOfResults() {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceTeamDefeat(PlayerTeams team) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announcePlayerRename(String oldname, String newname) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceChatMessage(PlayerActionInterface player, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceGameStartETA(int seconds) {
		// TODO Auto-generated method stub

	}

}
