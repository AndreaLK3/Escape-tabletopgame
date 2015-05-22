package it.escape.server.model.game.gamemap;

import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.cell.actions.GetSectorCardAction;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

public class DangerousCell extends Cell {

	public DangerousCell(PositionCubic position) {
		super(position);
	}

	@Override
	public boolean canEnter(Player curPlayer) {
		return true;
	}
	

	@Override
	public CellAction getCellAction() {
		return new GetSectorCardAction();
	}

	@Override
	public String toString() {
		return "DangerousCell(coord=" + position.toString() + ")";
	}

}
