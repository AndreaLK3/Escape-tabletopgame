package it.escape.server.model.game.Actions;

import it.escape.server.controller.Player;


public interface PlayerAction {
	
	public void execute (Player currentPlayer);
}
