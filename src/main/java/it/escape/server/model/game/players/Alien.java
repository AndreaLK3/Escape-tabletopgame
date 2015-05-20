package it.escape.server.model.game.players;


public class Alien extends Player {
	
public final static int ALIENSMAXRANGE = 2;
	
	public Alien () {
		super();
		maxRange = ALIENSMAXRANGE;
	}
	
	public PlayerTeams getTeam(){
		return PlayerTeams.ALIENS;
	}

	@Override
	public void endOfTurn() {
	}

	@Override
	public void die() {
		alive = false;
	};
}
	
