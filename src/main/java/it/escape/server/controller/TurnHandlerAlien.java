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


	@Override
	public void executeTurnSequence() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void turnBeforeMove() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void turnMove() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void turnLand() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void turnAfterMove() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
