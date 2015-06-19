package it.escape.core.server.model.game.gamemap;

import java.util.logging.Logger;

import it.escape.core.server.controller.game.actions.CellAction;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.core.server.model.game.players.Player;
import it.escape.tools.utils.LogHelper;

public abstract class Cell {
	
	protected static final Logger log = Logger.getLogger( Cell.class.getName() );
	
	protected PositionCubic position;
	
	public PositionCubic getPosition() {
		return position;
	}
	
	public Cell(PositionCubic position) {
		LogHelper.setDefaultOptions(log);
		this.position = position;
	}
	
	public abstract CellAction getCellAction();
	
	public abstract boolean canEnter(Player curPlayer);
	
}
