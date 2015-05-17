package it.escape.server.model.game.gamemap;

import java.util.logging.Logger;

import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.GameTypes;
import it.escape.server.model.game.cards.Card;

/**This is an abstract class, we can not create objects of this type.
 * It contains:
 * 1) a static factory method for creating a certain kind of escape hatch, depending on the chosen game mode.
 * 2) an abstract method that returns a ShuttleState, depending on which card is drawn
 * 3) an abstract method, tryHatch()
 * This class is extended by the classes AlwaysOpenShuttle, ClosedShuttle, UnknownShuttle, OpenOnceShuttle,
 * which represent the actual state of the Escape Cell.* 
 * @author andrea
 */
abstract class ShuttleState {
	
	protected static final Logger log = Logger.getLogger( ShuttleState.class.getName() );

	static ShuttleState shuttleFactory(GameMode mode) {
		if (mode.getType()==GameTypes.BASE) 
			return new AlwaysOpenShuttle();
		else
			return new UnknownShuttle();
	}
	
	
	abstract ShuttleState decideState(Card aCard);
	
	public abstract boolean tryHatch();

		
}
