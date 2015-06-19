package it.escape.core.server.controller.game.actions;

import it.escape.core.server.model.game.cards.ObjectCard;

public interface PlayerActionInterface {
	
	public void addCard(ObjectCard card);
	
	public void setEscaped();
	
	public String getName();
	
	public boolean isAlive();
	
	public void die();
	
	public void changeName(String newname);
}
