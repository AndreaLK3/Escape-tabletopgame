package it.escape.server.model.game.gamemap;

public class OpenOnceShuttle extends ShuttleState {

	@Override
	public boolean tryHatch() {
		System.out.println("You managed to find an active escape shuttle!");
		return true;
	}

}
