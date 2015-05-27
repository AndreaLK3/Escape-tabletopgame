package it.escape.server.model.game.players;

import java.util.List;

public class JoinPlayerList {
	
	private List<Player> players;

	public JoinPlayerList(List<Player> players) {
		this.players = players;
	}

	public String join(String separator) {
		StringBuilder ret = new StringBuilder();
		
		for (int i = 0; i < players.size(); i++) {
			ret.append(players.get(i).getName());
			if (i+1 < players.size()) {
				ret.append(separator);
			}
		}
		
		return ret.toString();
	}
}
