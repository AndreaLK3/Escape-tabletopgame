package it.escape.server.controller;

import it.escape.server.controller.game.actions.MapActionInterface;
import it.escape.server.model.game.players.Alien;
import it.escape.server.model.game.players.Player;

public class TurnHandlerAlien extends TurnHandler{

	Alien currentPlayer;
	
	
	public TurnHandlerAlien(Player currentPlayer, MapActionInterface map) {
		super(map);
		this.currentPlayer=(Alien)currentPlayer;
	}
}
