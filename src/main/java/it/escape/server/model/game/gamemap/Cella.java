package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public abstract class Cella {
	
	private PositionCubic position;
	
	public PositionCubic getPosition() {
		return position;
	}
	
	public Cella(PositionCubic position) {
		this.position = position;
	}
	
	public abstract void eseguiAzione(Action esecutore);
	
	public abstract boolean isWalkable(Action esecutore);
	
}
