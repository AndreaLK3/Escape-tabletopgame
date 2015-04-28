package it.escape.server.model.game.character;

import it.escape.server.model.game.PlayerTeams;

public interface Action {
		
	public void drawCard();
	
	public void escape();
	
	public void noAction();
}
