package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.Actions.PlayerAction;
import it.escape.server.model.game.character.GameCharacter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class DangerousCell extends Cell {

	public DangerousCell(PositionCubic position) {
		super(position);
	}

	@Override
	public void doAction() {
	
	}

	@Override
	public boolean isWalkable(GameCharacter character) {
		return true;
	}

	@Override
	public String toString() {
		return "DangerousCell(coord=" + position.toString() + ")";
	}
}
