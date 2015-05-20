package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;
import it.escape.server.model.game.gamemap.DangerousCell;
import it.escape.server.model.game.gamemap.EscapeCell;
import it.escape.server.model.game.gamemap.SafeCell;
import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.GameTypes;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.server.model.game.players.PlayerTeams;
import it.escape.strings.StringRes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * classe accessoria, gestisce la conversione JSON->Object
 * di una *singola* Cell dell'array
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
	private Cell mapper(PositionCubic pos, String tipo) throws BadJsonFileException {
		if (tipo.equals(StringRes.getString("mapfile.stringToClass.CellaSicura"))) {
			return new SafeCell(pos);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.CellaPericolosa"))) {
			return new DangerousCell(pos);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.Cellapartenza.umani"))) {
			return StartingCell.getStart(pos,PlayerTeams.HUMANS);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.Cellapartenza.alieni"))) {
			return StartingCell.getStart(pos,PlayerTeams.ALIENS);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.CellaScialuppa"))) {
			return new EscapeCell(pos, new GameMode(GameTypes.BASE));
		}
		
		throw new BadJsonFileException();
	}
	
	Cell convert() throws JSONException, BadJsonFileException {
		PositionCubic pos;
		try {
			pos = CoordinatesConverter.fromAlphaNumToCubic(
					entry.getString(StringRes.getString("mapfile.json.cells.alphanum")));
		} catch (BadCoordinatesException e) {
			throw new BadJsonFileException();
		}
		String tipo = entry.getString(StringRes.getString("mapfile.json.cells.tipo"));
		return mapper(pos, tipo);
	}
}
