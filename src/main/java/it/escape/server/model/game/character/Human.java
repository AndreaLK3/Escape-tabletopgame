package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;

public class Human extends Player {
	
	public PlayerTeams getTeam(){
		return PlayerTeams.HUMANS;
	};
	
}