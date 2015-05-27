package it.escape.server.model.game;

import it.escape.server.model.game.cards.ObjectCard;
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
 * Each running game will instance only one announcer (it is a singleton)
 * @author michele
 */
public class Announcer extends Observable {
	
	private String message;
	
	private static Announcer instance = null;
	
	public static Announcer getAnnouncerInstance() {
		if (instance == null) {
			instance = new Announcer();
		}
		return instance;
	}
	
	private Announcer() {
		
	}
	
	public void announcePlayerConnected(Player player) {
		String newmsg = String.format(StringRes.getString("messaging.playerConnected"),
				player.getName());
		announce(newmsg);
	}
	
	public void announcePlayerDisconnected(Player player) {
		String newmsg = String.format(StringRes.getString("messaging.playerDisonnected"),
				player.getName());
		announce(newmsg);
	}
	
	public void announceAttack(Player player, PositionCubic position) {
		// encode player and position to a string
		// use announce to send that string
	}
	
	public void announceNoise(String location) {
		// encode position to a string
		// use announce to send that string
	}
	
	public void announceObjectCard(Player player, ObjectCard theCard) {
		
		
	}
	
	public void announceDeath(Player victim) {
		
		
	}
	
	public void announce(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return message;
	}

	public void announcePlayerPosition(Player p, PositionCubic position) {
		
	}

	public void announceEscape(Player currentPlayer) {
		
	}
	
	public void announceGameEnd() {
		String newmsg = StringRes.getString("messaging.endOfTheGame");
		announce(newmsg);
	}
	
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		String newmsg = String.format(StringRes.getString("messaging.winnerTeam"),
				team.toString(),
				new JoinPlayerList(members).join(", "));
		announce(newmsg);
	}
	
	public void announceTeamDefeat(PlayerTeams team) {
		String newmsg = String.format(StringRes.getString("messaging.loserTeam"),
				team.toString());
		announce(newmsg);
	}
}
