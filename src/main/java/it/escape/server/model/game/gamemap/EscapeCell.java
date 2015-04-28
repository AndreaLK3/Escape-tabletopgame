package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

/**
 * Implementazione temporanea; da modificare!
 * @author andrea
 *
 */
public class EscapeCell extends Cell {
	
	private ShuttleState state;
	
	public EscapeCell(PositionCubic position) {
		super(position);
	}
	
	@Override
	public void doAction(Action esecutore) {
			esecutore.escape();
	}

		public boolean isWalkable(Action esecutore) {
		/*if (esecutore.tipo==HUMANS)
			return true
			else
			{*/	System.out.println("Un alieno non puo' accedere a una scialuppa!");
				return false;//}
	}
	

}
