package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.character.Action;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import java.util.List;

public class CellaPartenza extends Cella {
	
	private static ArrayList<CellaPartenza> cellePartenza = new ArrayList<>;
	
	SpawnType tipo;

	private CellaPartenza(PositionCubic position, SpawnType tipo) {
		super(position);
		this.tipo = tipo;
	}
	
	public static CellaPartenza getPartenza(SpawnType categoria) {
		for (CellaPartenza partenza : cellePartenza)
			if (partenza!=null && partenza.tipo==SpawnType.categoria)
				return partenza;	
		cellePartenza[a] = 	
	
	public static getCellaPartenza() {
		
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
