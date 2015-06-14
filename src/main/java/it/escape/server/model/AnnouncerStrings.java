package it.escape.server.model;

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
 * AnnouncerStrings class: simple observable, relays a message to the observers;
 * those are located in the server view (our net-communication threads).
 * Each running game will instance only one announcer
 * @author michele
 */
public class AnnouncerStrings extends Observable implements Announcer {
	
	private String message;
	
	public AnnouncerStrings() {
		
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announcePlayerConnected(int, int)
	 */
	@Override
	public void announcePlayerConnected(int connected, int maximum) {
		String newmsg = String.format(StringRes.getString("messaging.playerConnected"),
				connected,
				maximum);
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announcePlayerDisconnected(it.escape.server.controller.game.actions.PlayerActionInterface)
	 */
	@Override
	public void announcePlayerDisconnected(PlayerActionInterface player) {
		String newmsg = String.format(StringRes.getString("messaging.playerDisconnected"),
				player.getName());
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceAttack(it.escape.server.controller.game.actions.PlayerActionInterface, it.escape.server.model.game.gamemap.positioning.PositionCubic)
	 */
	@Override
	public void announceAttack(PlayerActionInterface player, PositionCubic position) {
		String newmsg = String.format(StringRes.getString("messaging.playerAttacking"),
				player.getName(),
				CoordinatesConverter.fromCubicToAlphaNum(position));
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceNoise(java.lang.String)
	 */
	@Override
	public void announceNoise(String location) {
		String newmsg = String.format(StringRes.getString("messaging.noise"),
						CoordinatesConverter.prettifyAlphaNum(location));
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceObjectCard(it.escape.server.controller.game.actions.PlayerActionInterface, it.escape.server.model.game.cards.ObjectCard)
	 */
	@Override
	public void announceObjectCard(PlayerActionInterface player, ObjectCard theCard) {
		String newmsg = String.format(StringRes.getString("messaging.playerIsUsingObjCard"),
				player.getName(),
				theCard.getClass().getSimpleName(),
				player.getName());
		announce(newmsg);
	}
	
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceDeath(it.escape.server.controller.game.actions.PlayerActionInterface)
	 */
	@Override
	public void announceDeath(PlayerActionInterface victim) {
		String newmsg = String.format(StringRes.getString("messaging.playerDied"),
				victim.getName());
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceDefense(it.escape.server.model.game.gamemap.positioning.PositionCubic)
	 */
	@Override
	public void announceDefense(PositionCubic position) {
		String msg = String.format(StringRes.getString("messaging.playerDefended"), 
				CoordinatesConverter.fromCubicToAlphaNum(position));
		announce(msg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announce(java.lang.String)
	 */
	@Override
	public void announce(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announcePlayerPosition(it.escape.server.controller.game.actions.PlayerActionInterface, it.escape.server.model.game.gamemap.positioning.PositionCubic)
	 */
	@Override
	public void announcePlayerPosition(PlayerActionInterface p, PositionCubic position) {
		String newmsg = String.format(StringRes.getString("messaging.disclosePlayerPosition"),
				p.getName(),
				CoordinatesConverter.fromCubicToAlphaNum(position));
		announce(newmsg);
	}

	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceEscape(it.escape.server.controller.game.actions.PlayerActionInterface)
	 */
	@Override
	public void announceEscape(PlayerActionInterface currentPlayer) {
		String newmsg = String.format(StringRes.getString("messaging.playerEscaped"),
				currentPlayer.getName(),
				StringRes.getString("ship_name"));
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceGameEnd()
	 */
	@Override
	public void announceGameEnd() {
		String newmsg = StringRes.getString("messaging.endOfTheGame");
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceEndOfResults()
	 */
	@Override
	public void announceEndOfResults() {
		String newmsg = StringRes.getString("messaging.endOfResults");
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceTeamVictory(it.escape.server.model.game.players.PlayerTeams, java.util.List)
	 */
	@Override
	public void announceTeamVictory(PlayerTeams team, List<Player> members) {
		String newmsg = String.format(
				StringRes.getString("messaging.winnerTeam"),
				team.toString(),
				new JoinPlayerList(members).join(StringRes.getString("messaging.playerListSeparator")));
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceTeamDefeat(it.escape.server.model.game.players.PlayerTeams)
	 */
	@Override
	public void announceTeamDefeat(PlayerTeams team) {
		String newmsg = String.format(StringRes.getString("messaging.loserTeam"),
				team.toString());
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announcePlayerRename(java.lang.String, java.lang.String)
	 */
	@Override
	public void announcePlayerRename(String oldname, String newname) {
		String newmsg = String.format(
				StringRes.getString("messaging.announceRename"),
				oldname,
				newname);
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceChatMessage(it.escape.server.controller.game.actions.PlayerActionInterface, java.lang.String)
	 */
	@Override
	public void announceChatMessage(PlayerActionInterface player, String message) {
		String newmsg = String.format(
				StringRes.getString("messaging.relayChat"),
				player.getName(),
				message);
		announce(newmsg);
	}
	
	/* (non-Javadoc)
	 * @see it.escape.server.model.Announcer#announceGameStartETA(int)
	 */
	@Override
	public void announceGameStartETA(int seconds) {
		String newmsg = String.format(
				StringRes.getString("messaging.gameStartETA"),
				seconds);
		announce(newmsg);
	}
}
