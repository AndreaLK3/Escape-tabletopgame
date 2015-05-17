package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.gamemap.GameMap;

public class Player {

	protected Card aCard;
	
	public Player () {
	}
	
	/*
	public void drawSectorCard() {
		drawCard(decksRef.getsDeck());
		aCard.effect(myChar);
	}
	public void drawObjectCard() {
		drawCard(decksRef.getoDeck());
		aCard.effect(this);
	}
	
	/** draw a card from a deck passed as parameter
	 * @param aDeck
	 */
	private void drawCard(Deck aDeck) {
		aCard = aDeck.drawCard();
	}

	/**This method is overridden by the Human and Alien subclasses*/
	public PlayerTeams getTeam(){
		return null;
	};






}
