package it.escape.core.server.model.game.players;



public class Alien extends Player {
	
	private final static int ALIENSMAXRANGE = 2;
	
	public Alien (String name) {
		super(name);
		maxRange = ALIENSMAXRANGE;
	}
	
	public PlayerTeams getTeam(){
		return PlayerTeams.ALIENS;
	}


	@Override
	public void die() {
		alive = false;
	};
}
	
