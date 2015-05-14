package it.escape.server.controller;

import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.cards.Card;
import it.escape.server.model.game.cards.Deck;
import it.escape.server.model.game.cards.DecksHandler;
import it.escape.server.model.game.character.GameCharacter;
import it.escape.server.model.game.gamemap.Mappa;

public class Player {

	private GameCharacter myChar;
	private Mappa theMap;
	private DecksHandler decksRef;
	protected Card aCard;
	private PlayerAction currentAction;
	
	public Player (DecksHandler decksRef, Mappa theMap) {
		this.decksRef = decksRef;
		this.theMap = theMap;
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

	public GameCharacter getMyCharacter() {
		return myChar;
	}

	public Mappa getTheMap() {
		return theMap;
	}






}
