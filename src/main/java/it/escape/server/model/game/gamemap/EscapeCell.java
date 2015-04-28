package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

/**
 * Implementazione temporanea; da modificare!
 * @author andrea
 *
 */
public class EscapeCell extends Cell {
	
	private ShuttleState state;
	
	public EscapeCell(PositionCubic position, GameMode mode) {
		super(position);
		state = ShuttleState.shuttleFactory(mode);
	}
	
	@Override
	public void doAction(Action esecutore) {
		if (state.tryHatch()==true)	
			esecutore.escape();
	}

		public boolean isWalkable(Action esecutore) {
		/*if (esecutore.tipo==HUMANS)
			return true
			else
			{*/	System.out.println("An alien can't use an escape shuttle!");
				return false;//}
	}
	

}
