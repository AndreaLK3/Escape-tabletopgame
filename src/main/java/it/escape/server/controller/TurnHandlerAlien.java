package it.escape.server.controller;

import it.escape.server.model.game.character.Alien;
import it.escape.server.model.game.character.Human;
import it.escape.server.model.game.character.Player;

public class TurnHandlerAlien extends TurnHandler{

	Alien currentPlayer;
	
	
	public TurnHandlerAlien(Alien currentPlayer) {
		this.currentPlayer=currentPlayer;
	}
}
