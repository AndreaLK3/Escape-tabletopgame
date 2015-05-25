package it.escape.server.model.game.gamemap;

import it.escape.server.controller.game.actions.CellAction;
import it.escape.server.controller.game.actions.cardactions.NoCardAction;
import it.escape.server.controller.game.actions.cellactions.GetEscapeCardAction;
import it.escape.server.controller.game.actions.cellactions.NoCellAction;
import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Human;
import it.escape.server.model.game.players.Player;

/**
 * This is the cell that contains the EscapeShuttle. It can be reached only by humans.
 * Its state is defined at run-time, depending on the GameMode, and possibly on EscapeShuttleCard that is drawn.
 * @author andrea
 */
public class EscapeCell extends Cell {
	
	private boolean alreadyUsed;
	
	
	/**Constructor:
	 * @param position - Remember that EscapeCell extends Cell, therefore it knows its own position
	 */
	public EscapeCell(PositionCubic position, GameMode mode) {
		super(position);
		alreadyUsed = false;
	}
	
	
	@Override
		public boolean canEnter(Player player) {
		if (player instanceof Human )
			return true;
			else
			{	log.info("An alien can't use an escape shuttle!");
				return false;}
	}

	/**Since this is the EscapeCell that is used playing the Complete Mode,
	 * if the player is the first to get here, it causes him to draw an Escape Card.
	 * (The Escape Card might allow the Player to Escape).
	 * If the Player is not the first to get here, this EscapeCell can not be 
	 * used, and it returns NoCellAction.
	 */
	@Override
	public CellAction getCellAction() {
		if (!alreadyUsed) {
			alreadyUsed = true;
			return new GetEscapeCardAction();
		}
		else 
			return new NoCellAction();
	}
	
	
	@Override
	public String toString() {
		return "EscapeShuttleCell(coord=" + position.toString() + ")";
	}
	

}
