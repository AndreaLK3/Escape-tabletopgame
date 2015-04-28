package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

import java.util.ArrayList;

/**Questa classe definisce le Caselle di Partenza, una per tipo (Umani, Alieni)
 * Le caselle di partenza sono implementate come dei singleton: 
 * Se una è già presente, non ne viene creata un'altra.
 * @author andrea
 */
public class CellaPartenza extends Cella {
		
		private static ArrayList<CellaPartenza> cellePartenza = new ArrayList<CellaPartenza>();
		
		PlayerTypes tipo;

		private CellaPartenza(PositionCubic position, PlayerTypes tipo) {
			super(position);
			this.tipo = tipo;
		}
		
		
		public static CellaPartenza getPartenza(PositionCubic position, PlayerTypes tipo) {
			
			CellaPartenza nuovaPartenza;
			
			for (CellaPartenza partenza : cellePartenza)
				if (partenza!=null)
					if (partenza.tipo==tipo)
						return partenza;	
			
			 nuovaPartenza= new CellaPartenza(position, tipo);
			 cellePartenza.add(nuovaPartenza);
			 return nuovaPartenza;
		}

		
		@Override
		public void eseguiAzione(Action esecutore) {
			esecutore.noAction();
		}

		@Override
		public boolean isWalkable(Action esecutore) {
			return false;
	}
	
	@Override
	public String toString() {
		return "CellaPartenza(coord=" + position.toString() + ", tipo=" + tipo.toString() + ")";
	}
}
