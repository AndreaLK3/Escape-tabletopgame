package it.escape.server.controller;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.ArrayList;
import java.util.List;

public class TurnHandler {
	
	protected Player currentPlayer;
	
	public TurnHandler (Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void beginTurn() {} ;
	
}
