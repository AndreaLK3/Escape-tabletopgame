package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

/**
 * This is the cell that contains the EscapeShuttle. It can be reached only by humans.
 * Its state can change at run-time, depending on the EscapeShuttleCard that is drawn.
 * @author andrea
 */
public class EscapeCell extends Cell {
	
	/**
	 * This is the variable that contains the object with the actual behavior of the escape hatch
	 */
	private ShuttleState state;
	
	/**This constructor calls the factory method in ShuttleState, that builds the 
	 * needed type of EscapeCell (AlwaysOpen, Unknown...) 
	 * @param position - Remember that EscapeCell extends Cell, therefore it knows its own position
	 * @param mode - the GameMode, so that the factory knows which kind of EscapeCell to build
	 */
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
		/*if (esecutore.team==HUMANS)
			return true
			else
			{*/	System.out.println("An alien can't use an escape shuttle!");
				return false;//}
	}
		
	@Override
	public String toString() {
		return "EscapeShuttleCell(coord=" + position.toString() + ", state=" + state.toString() + ")";
	}
	

}
