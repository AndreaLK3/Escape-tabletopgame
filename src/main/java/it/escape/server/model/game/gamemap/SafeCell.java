package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.Actions.PlayerAction;
import it.escape.server.model.game.character.GameCharacter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class SafeCell extends Cell {

	public SafeCell(PositionCubic position) {
		super(position);
	}

	@Override
	public void doAction() {
		
	}

	@Override
	public boolean isWalkable(GameCharacter esecutore) {
		return true;
	}
	
	@Override
	public String toString() {
		return "CellaSicura(coord=" + position.toString() + ")";
	}

}
