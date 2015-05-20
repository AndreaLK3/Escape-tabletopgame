package it.escape.server.controller;

import it.escape.server.model.Model;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Player;

public class TurnHandlerAlien extends TurnHandler{

	Alien currentPlayer;
	
	
	public TurnHandlerAlien(Player currentPlayer) {
		this.currentPlayer=(Alien)currentPlayer;
	}
}
