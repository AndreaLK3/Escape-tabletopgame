package it.escape.server.model.game.character;

import it.escape.server.model.game.cards.Card;

public class Player {

	protected Card aCard;
	protected int maxRange;

	
	public Player () {
	}
	



	public int getMaxRange() {
		return maxRange;
	};

	public void setMaxRange(int newRange) {
		maxRange = newRange;
	};






}
