package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class Cella {
	
	private PositionCubic position;
	
	public PositionCubic getPosition() {
		return position;
	}
	
	public Cella(PositionCubic position) {
		this.position = position;
	}
	
}
