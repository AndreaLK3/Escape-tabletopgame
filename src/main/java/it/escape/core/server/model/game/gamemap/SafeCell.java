package it.escape.core.server.model.game.gamemap;

import it.escape.core.server.controller.game.actions.CellAction;
import it.escape.core.server.controller.game.actions.cellactions.NoCellAction;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.core.server.model.game.players.Player;

public class SafeCell extends Cell {

	public SafeCell(PositionCubic position) {
		super(position);
	}

	@Override
	public boolean canEnter(Player curPlayer) {
		return true;
	}
	
	@Override
	public CellAction getCellAction() {
		return new NoCellAction();
	}
	
	@Override
	public String toString() {
		return "CellaSicura(coord=" + position.toString() + ")";
	}

	

}
