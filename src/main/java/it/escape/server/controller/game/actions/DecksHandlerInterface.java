package it.escape.server.controller.game.actions;

import it.escape.server.model.game.cards.ObjectCard;
import it.escape.server.model.game.cards.SectorCard;

public interface DecksHandlerInterface {
	
	public SectorCard drawSectorCard();

	public ObjectCard drawObjectCard();

	public SectorCard drawEscapeCard();
	
}
