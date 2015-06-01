package it.escape.server.model.game;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.JoinPlayerList;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;

import java.util.List;
import java.util.Observable;

/**
 * Announcer class: simple observable, relays a message to the observers;
 * those are located in the server view (our net-communication threads).
 * Each running game will instance only one announcer
 * @author michele
 */
public class Announcer extends Observable {
	
	private String message;
	
	public Announcer() {
		
	}
	
	/**
	 * Announce that a new player has connected, and shows the number
	 * of players currently connected (over the maximum allowed)
	 * @param connected
	 * @param maximum
	 */
	public void announcePlayerConnected(int connected, int maximum) {
		String newmsg = String.format(StringRes.getString("messaging.playerConnected"),
				connected,
				maximum);
		announce(newmsg);
	}
	
	public void announcePlayerDisconnected(PlayerActionInterface player) {
		String newmsg = String.format(StringRes.getString("messaging.playerDisconnected"),
				player.getName());
		announce(newmsg);
	}
	
	public void announceAttack(PlayerActionInterface player, PositionCubic position) {
		String newmsg = String.format(StringRes.getString("messaging.playerAttacking"),
				player.getName(),
				CoordinatesConverter.fromCubicToAlphaNum(position));
		announce(newmsg);
	}
	
	public void announceNoise(String location) {
		String newmsg = String.format(StringRes.getString("messaging.noise"),
				location);
		announce(newmsg);
	}
	
	public void announceObjectCard(PlayerActionInterface player, ObjectCard theCard) {
		String newmsg = String.format(StringRes.getString("messaging.playerIsUsingObjCard"),
				player.getName(),
				theCard.getClass().getSimpleName(),
				player.getName());
		announce(newmsg);
	}
	
	public void announceDeath(PlayerActionInterface victim) {
		String newmsg = String.format(StringRes.getString("messaging.playerDied"),
				victim.getName());
		announce(newmsg);
	}
	
	public void announce(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return message;
	}

	public void announcePlayerPosition(PlayerActionInterface p, PositionCubic position) {
		String newmsg = String.format(StringRes.getString("messaging.disclosePlayerPosition"),
				p.getName(),
				CoordinatesConverter.fromCubicToAlphaNum(position));
		announce(newmsg);
	}

	public void announceEscape(PlayerActionInterface currentPlayer) {
		String newmsg = String.format(StringRes.getString("messaging.playerEscaped"),
				currentPlayer.getName(),
				StringRes.getString("ship_name"));
		announce(newmsg);
	}
	
	public void announceGameEnd() {
		String newmsg = StringRes.getString("messaging.endOfTheGame");
		announce(newmsg);
	}
	
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		String newmsg = String.format(
				StringRes.getString("messaging.winnerTeam"),
				team.toString(),
				new JoinPlayerList(members).join(", "));
		announce(newmsg);
	}
	
	public void announceTeamDefeat(PlayerTeams team) {
		String newmsg = String.format(StringRes.getString("messaging.loserTeam"),
				team.toString());
		announce(newmsg);
	}
	
	public void announcePlayerRename(String oldname, String newname) {
		String newmsg = String.format(
				StringRes.getString("messaging.announceRename"),
				oldname,
				newname);
		announce(newmsg);
	}
	
	public void announceChatMessage(PlayerActionInterface player, String message) {
		String newmsg = String.format(
				StringRes.getString("messaging.relayChat"),
				player.getName(),
				message);
		announce(newmsg);
	}
}
