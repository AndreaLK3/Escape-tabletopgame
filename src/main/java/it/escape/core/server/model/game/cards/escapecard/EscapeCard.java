package it.escape.core.server.model.game.cards.escapecard;

import it.escape.core.server.controller.game.actions.CardAction;
import it.escape.core.server.controller.game.actions.cardactions.CanNotEscape;
import it.escape.core.server.controller.game.actions.cardactions.Escape;
import it.escape.core.server.model.game.cards.Card;
import it.escape.core.server.model.game.cards.SectorCard;

public class EscapeCard implements SectorCard,Card {
	
	private EscapeCardColor color;

	
	public EscapeCardColor getColor() {
		return color;
	}

	/**Constructor; it has the color (Enum: RED or GREEN) as a parameter*/
	public EscapeCard(EscapeCardColor color) {
		this.color = color;
	} 
	
	@Override
	public String toString() {
		return ("Escape card: its color is " + getColor().toString() );
		
	}

	@Override
	public CardAction getCardAction() {
		if (color.equals(EscapeCardColor.GREEN))
			return new Escape();
		else
			return new CanNotEscape();
	}
	

}
