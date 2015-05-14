package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.Actions.PlayerAction;
import it.escape.server.model.game.character.GameCharacter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public abstract class Cell {
	
	protected PositionCubic position;
	
	public PositionCubic getPosition() {
		return position;
	}
	
	public Cell(PositionCubic position) {
		this.position = position;
	}
	
	public abstract void doAction();
	
	public abstract boolean isWalkable(GameCharacter esecutore);
	
}
