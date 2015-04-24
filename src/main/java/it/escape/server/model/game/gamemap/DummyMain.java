package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.exceptions.BadCoordinatesException;

public class DummyMain {

	public static void main(String[] args) {
		try {
			PositionCubic coso = new PositionCubic(0,0,0);
			System.out.println(coso.toString());
		} catch (BadCoordinatesException e) {
			System.out.println("bad coord!");
		}
	}

}
