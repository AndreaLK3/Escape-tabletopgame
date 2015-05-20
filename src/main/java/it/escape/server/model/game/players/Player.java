package it.escape.server.model.game.players;


public abstract class Player {

	protected int maxRange;
	protected boolean hasMoved;
	protected boolean alive;
	
	public Player () {
		alive = true;
	}
	

	public void startTurn() {
		hasMoved = false;
	}


	public int getMaxRange() {
		return maxRange;
	};

	public void setMaxRange(int newRange) {
		maxRange = newRange;
	};
	
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * differently implemented in aliens and humans (humans can defend)
	 */
	public abstract void die();

	/**
	 * clean up sedatives / adrenaline effect (in the human)
	 */
	public abstract void endOfTurn();

}
