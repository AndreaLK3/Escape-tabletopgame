package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.StartingCell;
import it.escape.server.model.game.gamemap.DangerousCell;
import it.escape.server.model.game.gamemap.EscapeCell;
import it.escape.server.model.game.gamemap.SafeCell;
import it.escape.server.model.game.GameMode;
import it.escape.server.model.game.GameTypes;
import it.escape.server.model.game.PlayerTeams;
import it.escape.server.model.game.gamemap.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.positioning.PositionCubic;
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
		if (tipo.equals(StringRes.getString("mapfile.stringToClass.SafeCell"))) {
			return new SafeCell(pos);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.DangerousCell"))) {
			return new DangerousCell(pos);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.StartingCell.umani"))) {
			return StartingCell.getStart(pos,PlayerTeams.HUMANS);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.StartingCell.alieni"))) {
			return StartingCell.getStart(pos,PlayerTeams.ALIENS);
		}
		else if (tipo.equals(StringRes.getString("mapfile.stringToClass.EscapeCell"))) {
			return new EscapeCell(pos, new GameMode(GameTypes.BASE));
		}
		
		throw new BadJsonFileException();
	}
	
	Cell convert() throws JSONException, BadJsonFileException {
		PositionCubic pos = new PositionCubic(
				entry.getInt(StringRes.getString("mapfile.json.cells.x")), 
				entry.getInt(StringRes.getString("mapfile.json.cells.y")),
				entry.getInt(StringRes.getString("mapfile.json.cells.z")) );
		String tipo = entry.getString(StringRes.getString("mapfile.json.cells.tipo"));
		
		return mapper(pos, tipo);
	}
}
