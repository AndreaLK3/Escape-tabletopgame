package it.escape.server.model.game.gamemap;

public class AlwaysOpenShuttle extends ShuttleState {

	@Override
	public boolean tryHatch() {
		System.out.println("You managed to reach an escape shuttle!");
		return true;
	}

}
