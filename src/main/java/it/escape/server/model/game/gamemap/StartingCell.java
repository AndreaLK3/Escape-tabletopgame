package it.escape.server.model.game.gamemap;

import it.escape.server.controller.game.actions.cellActions.CellAction;
import it.escape.server.controller.game.actions.cellActions.NoCellAction;
import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.Player;

import java.util.ArrayList;

/**This class defines the starting cells, one for each player type (Humans, Aliens)
 * The starting cell are implemented as singletons: 
 *For each kind, if one is already present on the map, another is not created.
 * @author andrea
 */
public class StartingCell extends Cell {
		
		private static ArrayList<StartingCell> startCells = new ArrayList<StartingCell>();
		
		PlayerTeams type;

		private StartingCell(PositionCubic position, PlayerTeams type) {
			super(position);
			this.type = type;
		}
		
		
		public static StartingCell getStart(PositionCubic position, PlayerTeams type) {
			
			StartingCell newStart;
			
			for (StartingCell start : startCells)
				if (start!=null)
					if (start.getType()==type)
						return start;	
			
			 newStart= new StartingCell(position, type);
			 startCells.add(newStart);
			 return newStart;
		}

		

		@Override
		public CellAction getCellAction() {
			return new NoCellAction();
		}
		
		public PlayerTeams getType() {
			return type;
		}

		@Override
		public boolean canEnter(Player curPlayer) {
			return false;
	}
		
	@Override
	public String toString() {
		return "StartingPosition(coord=" + position.toString() + ", type=" + type.toString() + ")";
	}


	

}
