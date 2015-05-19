package it.escape.server.model.game.character;


public class Player {

	protected int maxRange;
	protected boolean hasMoved;
	

	
	public Player () {
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






}
