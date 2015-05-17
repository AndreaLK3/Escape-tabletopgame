package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.character.Player;
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
	
	public abstract boolean canEnter(Player curPlayer);
	
}
