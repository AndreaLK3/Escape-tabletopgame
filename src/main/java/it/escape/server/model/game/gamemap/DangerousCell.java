package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.CellAction;
import it.escape.server.model.game.character.Character;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class DangerousCell extends Cell {

	public DangerousCell(PositionCubic position) {
		super(position);
	}

	@Override
	public void doAction(CellAction character) {
		character.drawSectorCard();
	}

	@Override
	public boolean isWalkable(Character character) {
		return true;
	}

	@Override
	public String toString() {
		return "DangerousCell(coord=" + position.toString() + ")";
	}
}
