package it.escape.server.model.game.players;


public class Human extends Player {
	
	private final static int HUMANSMAXRANGE = 1;
	
	private boolean sedatives;
	
	
	public Human () {
		super();
		maxRange = HUMANSMAXRANGE;
	}
	
	public PlayerTeams getTeam() {
		return PlayerTeams.HUMANS;
	}

	@Override
	public void endOfTurn() {
		if (sedatives) {
			sedatives = false;
		}
		// to be implemented: clean up adrenaline
	};
	
	public boolean hasSedatives() {
		return sedatives;
	}
	
	public void setSedatives() {
		sedatives = true;
	}

	@Override
	public void die() {
		// check defense
	}
}