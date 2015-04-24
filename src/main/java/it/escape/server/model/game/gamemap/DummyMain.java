package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.exceptions.BadCoordinatesException;

public class DummyMain {

	public static void main(String[] args) {
		
		String proveAlfaNum[] = {"A0", "A1", "A2", "B0", "B1", "B2"};
				
		for (String var1 : proveAlfaNum)
		{
			try {
				PositionCubic coso = CoordinatesConverter.fromAlphaNumToCubic(var1);
				System.out.println(coso.toString());
			} catch (BadCoordinatesException e) {
				System.out.println("The coordinates given are not acceptable!");
			}
		}

	}
}
