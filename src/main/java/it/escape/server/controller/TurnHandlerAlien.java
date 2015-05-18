package it.escape.server.controller;

import it.escape.server.model.game.character.Alien;
import it.escape.server.model.game.character.Player;

public class TurnHandlerAlien extends TurnHandler{

	Alien currentPlayer;
	
	
	public TurnHandlerAlien(Player currentPlayer) {
		this.currentPlayer=(Alien)currentPlayer;
	}
}
