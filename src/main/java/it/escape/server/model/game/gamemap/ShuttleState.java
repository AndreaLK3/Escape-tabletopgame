package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.GameTypes;

/**This is an abstract class, we can not create objects of this type.
 * It contains:
 * 1) a static factory method for creating a certain kind of escape hatch, depending on the chosen game mode.
 * 2) a static method that returns a ShuttleState, depending on which card is drawn 
 * (n: alternate implementation possible: every subtype a version of it)
 * 3) an abstract method, tryHatch()
 * This class is extended by the classes AlwaysOpenShuttle, ClosedShuttle, UnknownShuttle, OpenOnceShuttle,
 * which represent the actual state of the Escape Cell.* 
 * @author andrea
 */
abstract class ShuttleState {

	static ShuttleState shuttleFactory(GameMode mode) {
		if (mode.getType()==GameTypes.BASE) 
			return new AlwaysOpenShuttle();
		else
			return new UnknownShuttle();
	}
	
	
	static ShuttleState decideState() {
		/*
		 if (EscapeCard==RED), oppure if (EscapeCard()==false), o simili
		 	actualShuttle = new ClosedShuttle();
		 else
		 	actualShuttle = new OpenOnceShuttle();
		 */	
		 
		return new OpenOnceShuttle();
	}
	
	public abstract boolean tryHatch();

		
}
