package it.escape.server.model.game.gamemap;

public class UnknownShuttle extends ShuttleState {

	private ShuttleState actualShuttle;
	
	@Override
	public boolean tryHatch() {
		/*
		 if (EscapeCard==RED), oppure if (EscapeCard()==false), o simili
		 	actualShuttle == new ClosedShuttle();
		 else
		 	actualShuttle == new OpenOnceShuttle();
		 */
		actualShuttle = new OpenOnceShuttle();
		return actualShuttle.tryHatch();
	}

}
