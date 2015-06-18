package it.escape.server.controller;

import java.util.logging.Logger;

import it.escape.server.model.Announcer;
import it.escape.server.model.AnnouncerRMIBroadcast;
import it.escape.server.model.game.players.JoinPlayerList;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

/**
 * Auxiliary class to prevent code repetition in GameMaster
 * @author michele
 *
 */
public class VictoryAnnounce {
	
	private static final Logger LOGGER = Logger.getLogger( VictoryAnnounce.class.getName() );
	
	private Announcer announcer;
	
	private VictoryChecker victoryChecker;

	public VictoryAnnounce(Announcer announcer, VictoryChecker victoryChecker) {
		LogHelper.setDefaultOptions(LOGGER);
		this.announcer = announcer;
		this.victoryChecker = victoryChecker;
	}
	
	public void totalHumanWin() {
		LOGGER.fine("Announcing total victory of team HUMANS, winners: " + 
				new JoinPlayerList(victoryChecker.getHumanWinners()).join(StringRes.getString("messaging.playerListSeparator")));
		announcer.announceTeamVictory(
				PlayerTeams.HUMANS,
				victoryChecker.getHumanWinners());
		announcer.announceTeamDefeat(
				PlayerTeams.ALIENS);
	}
	
	public void totalAlienWin() {
		LOGGER.fine("Announcing total victory of team ALIENS, winners: " + 
				new JoinPlayerList(victoryChecker.getAlienWinners()).join(StringRes.getString("messaging.playerListSeparator")));
		announcer.announceTeamDefeat(
				PlayerTeams.HUMANS);
		announcer.announceTeamVictory(
				PlayerTeams.ALIENS,
				victoryChecker.getAlienWinners());
	}
	
	public void partialHumanWin() {
		LOGGER.fine("Announcing partial victory of team HUMANS, winners: " + 
				new JoinPlayerList(victoryChecker.getHumanWinners()).join(StringRes.getString("messaging.playerListSeparator")) + 
				" the ALIENS are also winners: " + 
				new JoinPlayerList(victoryChecker.getAlienWinners()).join(StringRes.getString("messaging.playerListSeparator")));
		announcer.announceTeamVictory(
				PlayerTeams.HUMANS,
				victoryChecker.getHumanWinners());
		announcer.announceTeamVictory(
				PlayerTeams.ALIENS,
				victoryChecker.getAlienWinners());
	}
}
