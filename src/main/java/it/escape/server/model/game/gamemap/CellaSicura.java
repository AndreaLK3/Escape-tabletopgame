package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class CellaSicura extends Cella {

	public CellaSicura(PositionCubic position) {
		super(position);
	}

	public void eseguiAzione(Action esecutore) {
		esecutore.noAction();
	}

}
