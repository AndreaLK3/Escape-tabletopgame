package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import java.util.ArrayList;

/**Questa classe definisce le Caselle di start, una per type (Umani, Alieni)
 * Le caselle di start sono implementate come dei singleton: 
 * Se una è già presente, non ne viene creata un'altra.
 * @author andrea
 */
public class StartingCell extends Cell {
		
		private static ArrayList<StartingCell> startCells = new ArrayList<StartingCell>();
		
		PlayerTypes type;

		private StartingCell(PositionCubic position, PlayerTypes type) {
			super(position);
			this.type = type;
		}
		
		
		public StartingCell getStart(PositionCubic position, PlayerTypes type) {
			
			StartingCell newStart;
			
			for (StartingCell start : startCells)
				if (start!=null)
					if (start.type==type)
						return start;	
			
			 newStart= new StartingCell(position, type);
			 startCells.add(newStart);
			 return newStart;
		}

		
		@Override
		public void doAction(Action esecutore) {
			esecutore.noAction();
		}

		@Override
		public boolean isWalkable(Action esecutore) {
			return false;
	}
	

}
