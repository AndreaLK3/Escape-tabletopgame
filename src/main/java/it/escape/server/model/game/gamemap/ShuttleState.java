package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.GameTypes;

abstract class ShuttleState {

	public static ShuttleState shuttleFactory(GameMode mode) {
		if (mode.getType()==GameTypes.BASE) 
			return new AlwaysOpenShuttle();
		else
			return new UnknownShuttle();
	};
	
	public abstract boolean tryHatch();
		
}
