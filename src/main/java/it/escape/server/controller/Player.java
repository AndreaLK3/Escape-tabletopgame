package it.escape.server.controller;

import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.character.CardAction;
import it.escape.server.model.game.character.GameCharacter;

public class Player {

	private GameCharacter myChar;
	private DecksHandler decksRef;
	protected Card aCard;
	
	public Player (DecksHandler decksRef) {
		this.decksRef = decksRef;
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

	public GameCharacter getCharacter() {
		return myChar;
	}




}
