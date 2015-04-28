package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.gamemap.Cella;
import it.escape.server.model.game.gamemap.CellaPartenza;
import it.escape.server.model.game.gamemap.CellaPericolosa;
import it.escape.server.model.game.gamemap.CellaScialuppa;
import it.escape.server.model.game.gamemap.CellaSicura;
import it.escape.server.model.game.gamemap.PlayerTypes;
import it.escape.server.model.game.gamemap.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.strings.StringRes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * classe accessoria, gestisce la conversione JSON->Object
 * di una *singola* cella dell'array
 * @author michele
 *
 */
public class CellGenerator {
	
	private final JSONObject entry;

	CellGenerator(JSONObject entry) {
		this.entry = entry;
	}
	
	/**
	 * funzione che effettua la mappatura stringa_"tipo" -> Classe java
	 * @param pos
	 * @param tipo
	 * @return
	 * @throws BadJsonFileException
	 */
	private Cella mapper(PositionCubic pos, String tipo) throws BadJsonFileException {
		if (tipo.equals(StringRes.getString("mapfile.stringToClass.CellaSicura"))) {
			return new CellaSicura(pos);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.CellaPericolosa"))) {
			return new CellaPericolosa(pos);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.Cellapartenza.umani"))) {
			return CellaPartenza.getPartenza(pos,PlayerTypes.HUMANS);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.Cellapartenza.alieni"))) {
			return CellaPartenza.getPartenza(pos,PlayerTypes.ALIENS);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.CellaScialuppa"))) {
			return new CellaScialuppa(pos);
		}
		
		throw new BadJsonFileException();
	}
	
	Cella convert() throws JSONException, BadJsonFileException {
		PositionCubic pos = new PositionCubic(
				entry.getInt(StringRes.getString("mapfile.json.cells.x")), 
				entry.getInt(StringRes.getString("mapfile.json.cells.y")),
				entry.getInt(StringRes.getString("mapfile.json.cells.z")) );
		String tipo = entry.getString(StringRes.getString("mapfile.json.cells.tipo"));
		
		return mapper(pos, tipo);
	}
}
