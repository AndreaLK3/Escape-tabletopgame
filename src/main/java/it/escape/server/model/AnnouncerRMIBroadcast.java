package it.escape.server.model;

import it.escape.client.connection.rmi.ClientRemoteInterface;
import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.JoinPlayerList;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;

import java.util.List;

/**
 * Announcer based on the RMI system, instead of being an observable
 * carrying a message string, it directly invokes specific
 * remote methods in everyone among its subscribers.
 * @author michele, andrea
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
		for (ClientRemoteInterface client : subscribed) {
			
		}

	}

	@Override
	public void announceAttack(PlayerActionInterface player,PositionCubic position) {
		
		String message = StringRes.getString("messaging.playerAttacking");
		String attacker = player.getName();
		String location = CoordinatesConverter.fromCubicToAlphaNum(position);
		
		for (ClientRemoteInterface client : subscribed) {
			client.eventAttack(attacker, location, message);
		}

	}

	@Override
	public void announceNoise(String location) {
		location = CoordinatesConverter.prettifyAlphaNum(location);
		for (ClientRemoteInterface client : subscribed) {
			client.eventNoise(location);
		}

	}

	@Override
	public void announceObjectCard(PlayerActionInterface player, ObjectCard theCard) {
		String playerName = player.getName();
		String cardClassName = theCard.getClass().getSimpleName();
		String message = StringRes.getString("messaging.playerIsUsingObjCard");
		for (ClientRemoteInterface client : subscribed) {
			client.eventObject(playerName, cardClassName, message);
		}

	}

	@Override
	public void announceDeath(PlayerActionInterface victim) {
		String message = StringRes.getString("messaging.playerDied");
		String playerKilled = victim.getName();
		for (ClientRemoteInterface client : subscribed) {
			client.eventDeath(playerKilled, message);
		}

	}

	@Override
	public void announceDefense(PositionCubic position) {
		String msg = String.format(StringRes.getString("messaging.playerDefended"), 
				CoordinatesConverter.fromCubicToAlphaNum(position));
		for (ClientRemoteInterface client : subscribed) {
			client.eventDefense(msg);
		}

	}

	/**Currently not used in the AnnouncerRMI; it is here because it is needed in the interface implementation*/
	@Override
	public void announce(String message) {}

	@Override
	public void announcePlayerPosition(PlayerActionInterface p,	PositionCubic position) {
		String playerName = p.getName();
		String location = CoordinatesConverter.fromCubicToAlphaNum(position);
		for (ClientRemoteInterface client : subscribed) {
			client.eventFoundPlayer(playerName, location);
		}

	}

	@Override
	public void announceEscape(PlayerActionInterface currentPlayer) {
		//TODO: We have nothing to handle a playerEscaped in UpdaterSwing

	}

	@Override
	public void announceGameEnd() {
		for (ClientRemoteInterface client : subscribed) {
			client.eventEndGame();;
		}

	}

	@Override
	public void announceEndOfResults() {
		for (ClientRemoteInterface client : subscribed) {
			client.endResults();
		}

	}

	@Override
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		String teamName = team.toString();
		String winnersNames = new JoinPlayerList(members).join(StringRes.getString("messaging.playerListSeparator"));
		for (ClientRemoteInterface client : subscribed) {
			client.setWinners(teamName, winnersNames);
			
		}

	}

	@Override
	public void announceTeamDefeat(PlayerTeams team) {
		String teamName = team.toString();
		for (ClientRemoteInterface client : subscribed) {
			client.setLoserTeam(teamName);
		}

	}

	@Override
	public void announcePlayerRename(String oldname, String newname) {
		for (ClientRemoteInterface client : subscribed) {
			client.renamePlayer(oldname, newname);;
		}

	}

	@Override
	public void announceChatMessage(PlayerActionInterface player, String message) {
		String author = player.toString();
		for (ClientRemoteInterface client : subscribed) {
			client.visualizeChatMsg(author, message);
		}

	}

	@Override
	public void announceGameStartETA(int seconds) {
		String message = String.format(	StringRes.getString("messaging.gameStartETA"),seconds);
		for (ClientRemoteInterface client : subscribed) {
			client.setStartETA(message);
		}

	}

}
