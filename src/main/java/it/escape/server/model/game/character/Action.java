package it.escape.server.model.game.character;

import it.escape.server.model.game.gamemap.PlayerTypes;

public interface Action {
		
	public void drawCard();
	
	public void escape();
	
	public void noAction();
}
