package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;

public class Human extends Player {
	
	public final static int HUMANSMAXRANGE = 1;
	
	public boolean sedatives;
	
	
	public Human () {
		super();
		maxRange = HUMANSMAXRANGE;
	}
	
	public PlayerTeams getTeam(){
		return PlayerTeams.HUMANS;
	};
	
}