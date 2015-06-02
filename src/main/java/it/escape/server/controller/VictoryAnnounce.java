package it.escape.server.controller;

import it.escape.server.model.game.Announcer;
import it.escape.server.model.game.players.PlayerTeams;

/**
 * Auxiliary class to prevent code repetition in GameMaster
 * @author michele
 *
 */
public class VictoryAnnounce {
	
	private Announcer announcer;
	
	private VictoryChecker victoryChecker;

	public VictoryAnnounce(Announcer announcer, VictoryChecker victoryChecker) {
		this.announcer = announcer;
		this.victoryChecker = victoryChecker;
	}
	
	public void totalHumanWin() {
		announcer.announceTeamVictory(
				PlayerTeams.HUMANS,
				victoryChecker.getHumanWinners());
		announcer.announceTeamDefeat(
				PlayerTeams.ALIENS);
	}
	
	public void totalAlienWin() {
		announcer.announceTeamDefeat(
				PlayerTeams.HUMANS);
		announcer.announceTeamVictory(
				PlayerTeams.ALIENS,
				victoryChecker.getAlienWinners());
	}
	
	public void partialHumanWin() {
		announcer.announceTeamVictory(
				PlayerTeams.HUMANS,
				victoryChecker.getHumanWinners());
		announcer.announceTeamVictory(
				PlayerTeams.ALIENS,
				victoryChecker.getAlienWinners());
	}
}
