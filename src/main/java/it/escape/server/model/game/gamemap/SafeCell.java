package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Character;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class SafeCell extends Cell {

	public SafeCell(PositionCubic position) {
		super(position);
	}

	@Override
	public void doAction(Character esecutore) {
		esecutore.noAction();
	}

	@Override
	public boolean isWalkable(Character esecutore) {
		return true;
	}
	
	@Override
	public String toString() {
		return "CellaSicura(coord=" + position.toString() + ")";
	}

}
