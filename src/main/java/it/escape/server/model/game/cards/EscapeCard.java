package it.escape.server.model.game.cards;

import it.escape.server.controller.game.actions.CardAction;
import it.escape.server.controller.game.actions.cardactions.NoCardAction;
import it.escape.server.controller.game.actions.cardactions.Escape;

public class EscapeCard implements Card {
	
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

	public CardAction getCardAction() {
		if (color.equals(EscapeCardColor.GREEN))
			return new Escape();
		else
			return new NoCardAction();
	}
	
	

}
