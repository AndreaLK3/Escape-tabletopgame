package it.escape.server.model;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;
import it.escape.server.model.game.players.PlayerTeams;

import java.util.List;

public interface Announcer {

	/**
	 * Announce that a new player has connected, and shows the number
	 * of players currently connected (over the maximum allowed)
	 * @param connected
	 * @param maximum
	 */
	public void announcePlayerConnected(int connected, int maximum);

	public void announcePlayerDisconnected(PlayerActionInterface player);

	public void announceAttack(PlayerActionInterface player,
			PositionCubic position);

	public void announceNoise(String location);

	public void announceObjectCard(PlayerActionInterface player,
			ObjectCard theCard);

	public void announceDeath(PlayerActionInterface victim);

	public void announceDefense(PositionCubic position);

	public void announce(String message);
	
	public void announceNewTurn(int turnNumber, String playerName);

	public void announcePlayerPosition(PlayerActionInterface p,
			PositionCubic position);

	public void announceEscape(PlayerActionInterface currentPlayer);

	public void announceGameEnd();

	public void announceEndOfResults();

	public void announceTeamVictory(PlayerTeams team, List<Player> members);

	public void announceTeamDefeat(PlayerTeams team);

	public void announcePlayerRename(String oldname, String newname);

	public void announceChatMessage(PlayerActionInterface player, String message);

	public void announceGameStartETA(int seconds);
	

}