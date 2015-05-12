package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.AzioneCella;
import it.escape.server.model.game.character.Character;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class DangerousCell extends Cell {

	public DangerousCell(PositionCubic position) {
		super(position);
	}

	@Override
	public void doAction(AzioneCella character) {
		//character.drawCard();
	}

	@Override
	public boolean isWalkable(Character chraracter) {
		return true;
	}

	@Override
	public String toString() {
		return "DangerousCell(coord=" + position.toString() + ")";
	}
}
