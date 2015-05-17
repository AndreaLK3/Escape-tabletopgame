package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.character.Human;
import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

/**
 * This is the cell that contains the EscapeShuttle. It can be reached only by humans.
 * Its state is defined at run-time, depending on the GameMode, and possibly on EscapeShuttleCard that is drawn.
 * @author andrea
 */
public class EscapeCell extends Cell {
	
	/**
	 * This is the variable that contains the object with the actual behavior of the escape hatch
	 */
	private ShuttleState state;
	
	/**This constructor calls the factory method in ShuttleState, that builds the 
	 * needed type of EscapeCell (AlwaysOpen, Unknown, OpenOnce, Closed) 
	 * @param position - Remember that EscapeCell extends Cell, therefore it knows its own position
	 * @param mode - the GameMode, so that the factory knows which kind of EscapeCell to build
	 */
	public EscapeCell(PositionCubic position, GameMode mode) {
		super(position);
		state = ShuttleState.shuttleFactory(mode);
	}
	
	/**
	 * 
	 * If the current state of the Escape Cell is unknown, 
	 * invoke the static method to decide it, depending on the drawn ShuttleCard.
	 * Then, proceed to invoke the method tryHatch() defined in the abstract class;
	 * every type of shuttle will respond accordingly.
	 */
	@Override
	public void doAction() {
		if (state instanceof UnknownShuttle)
			state = state.decideState(null);	//aggiungere una carta
		if (state.tryHatch()==true)	
		{}
	}

		public boolean canEnter(Player player) {
		if (player instanceof Human )
			return true;
			else
			{	System.out.println("An alien can't use an escape shuttle!");
				return false;}
	}
		
	@Override
	public String toString() {
		return "EscapeShuttleCell(coord=" + position.toString() + ", state=" + state.toString() + ")";
	}
	

}
