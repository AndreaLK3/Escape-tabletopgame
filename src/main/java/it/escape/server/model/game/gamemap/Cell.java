package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.AzioneCella;
import it.escape.server.model.game.character.Character;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public abstract class Cell {
	
	protected PositionCubic position;
	
	public PositionCubic getPosition() {
		return position;
	}
	
	public Cell(PositionCubic position) {
		this.position = position;
	}
	
	public abstract void doAction(AzioneCella esecutore);
	
	public abstract boolean isWalkable(Character esecutore);
	
}
