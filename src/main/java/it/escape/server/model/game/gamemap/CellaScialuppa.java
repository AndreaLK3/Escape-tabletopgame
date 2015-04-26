package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

/**
 * Implementazione temporanea; da modificare!
 * @author andrea
 *
 */
public class CellaScialuppa extends Cella {
	
	private StatoScialuppa state;
	
	public CellaScialuppa(PositionCubic position) {
		super(position);
	}
	
	@Override
	public void eseguiAzione(Action esecutore) {
		state = new ScialuppaAperta();
		if (state.tryHatch()==true)
			esecutore.escape();
	}

		public boolean isWalkable(Action esecutore) {
		/*if (esecutore.tipo==HUMANS)
			return true
			else
			{*/	System.out.println("Un alieno non puo' accedere a una scialuppa!");
				return false;//}
	}
		
	public class ScialuppaAperta implements StatoScialuppa {

		public boolean tryHatch() {
			System.out.println("This hatch is opened and I am able to use the ship!");		
			return true;
		}
		public StatoScialuppa handleScialuppaState() {
			return this;
		}
	}
	
	public class ScialuppaNonDisponibile implements StatoScialuppa {

		public boolean tryHatch() {
			System.out.println("Oh no! This hatch is closed!");		
			return false;
		}

		public StatoScialuppa handleScialuppaState() {
			return this;
		}
	}
	
	public /*o private?*/ class ScialuppaIgnota implements StatoScialuppa {

		public boolean tryHatch() {
			return false;
		}

		public StatoScialuppa handleScialuppaState() {
			//if(...)
			state = new ScialuppaAperta();
			//else
				//state = newScialuppaChiusa();
			return state;
		}
	}

}
