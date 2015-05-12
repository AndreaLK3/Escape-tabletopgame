package it.escape.server.model.supergame;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.ArrayList;
import java.util.List;

public class SuperGame {
	
	private List<Player> listOfPlayers;
	
	public SuperGame () {
		listOfPlayers = new ArrayList<Player>();
	}

	public void notifyNoise(PositionCubic pos){
		for (Player p : listOfPlayers)
			p.notifyNoise();
	};
	
}
