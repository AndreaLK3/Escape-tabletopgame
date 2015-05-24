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

		
	}


	@Override
	public void turnBeforeMove() {

		
	}


	@Override
	public void turnMove() {
	
		
	}


	@Override
	public void turnLand() {
		
		
	}


	@Override
	public void turnAfterMove() {
	
		
	}


	@Override
	public void initialize() {
	
		
	}


	@Override
	public void fillInDefaultChoices() {
		// TODO Auto-generated method stub
		
	}
}
