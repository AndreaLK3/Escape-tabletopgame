package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;

public class CellaPartenza extends Cella {
	
	private SpawnType tipo;

	private CellaPartenza(PositionCubic position, SpawnType tipo) {
		super(position);
		this.tipo = tipo;
	}

	@Override
	public void eseguiAzione(Action esecutore) {
		esecutore.noAction();
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

}
