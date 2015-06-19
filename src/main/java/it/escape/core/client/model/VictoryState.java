package it.escape.core.client.model;

import it.escape.core.server.model.game.players.PlayerTeams;
import it.escape.tools.strings.StringRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VictoryState {
	
	private List<String> humanWinners;
	
	private List<String> alienWinners;
	
	private boolean aliensDefeated;
	
	private boolean humansDefeated;

	public VictoryState() {
		humanWinners = new ArrayList<String>();
		alienWinners = new ArrayList<String>();
		aliensDefeated = false;
		humansDefeated = false;
	}
	
	public void setTeamDefeated(String name) {
		if (name.equals(PlayerTeams.HUMANS.toString())) {
			humansDefeated = true;
		} else if (name.equals(PlayerTeams.ALIENS.toString())) {
			aliensDefeated = true;
		}
	}
	
	public void addWinners(String team, String list) {
		if (team.equalsIgnoreCase(PlayerTeams.HUMANS.toString())) {
			addTeamWinners(list, humanWinners);
		} else if (team.equalsIgnoreCase(PlayerTeams.ALIENS.toString())) {
			addTeamWinners(list, alienWinners);
		}
	}
	
	private void addTeamWinners(String list, List<String> dest) {
		String parts[] = list.split(StringRes.getString("messaging.playerListSeparator"));
		dest.addAll(Arrays.asList(parts));
	}
	
	

	public List<String> getHumanWinners() {
		return humanWinners;
	}

	public List<String> getAlienWinners() {
		return alienWinners;
	}

	public boolean isAliensDefeated() {
		return aliensDefeated;
	}

	public boolean isHumansDefeated() {
		return humansDefeated;
	}
	
	
}
