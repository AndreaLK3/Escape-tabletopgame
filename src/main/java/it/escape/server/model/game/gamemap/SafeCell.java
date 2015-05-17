package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.actions.PlayerAction;
import it.escape.server.model.game.character.Player;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class SafeCell extends Cell {

	public SafeCell(PositionCubic position) {
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
		return "CellaSicura(coord=" + position.toString() + ")";
	}

}
