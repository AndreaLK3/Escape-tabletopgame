package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class DangerousCell extends Cell {

	public DangerousCell(PositionCubic position) {
		super(position);
	}

	@Override
	public void doAction() {
	
	}

	@Override
	public boolean canEnter(Player curPlayer) {
		return true;
	}

	@Override
	public String toString() {
		return "DangerousCell(coord=" + position.toString() + ")";
	}
}
