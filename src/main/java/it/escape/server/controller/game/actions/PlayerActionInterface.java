package it.escape.server.controller.game.actions;

import it.escape.server.model.game.cards.ObjectCard;

public interface PlayerActionInterface {
	
	public void addCard(ObjectCard card);
	
	public void setEscaped();
	
	public String getName();
	
	public boolean isAlive();
	
	public void die();
}
