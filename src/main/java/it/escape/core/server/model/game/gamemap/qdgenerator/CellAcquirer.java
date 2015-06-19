package it.escape.core.server.model.game.gamemap.qdgenerator;

import it.escape.core.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.tools.strings.StringRes;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * abstract class used to create the various JSON cell-objects.
 * the mechanism makes use of pattern state.
 * @author michele
 *
 */
public abstract class CellAcquirer {
	
	/**
	 * side effect: a new cell JSON object is appended to the list
	 * @param appendTO
	 * @param coord
	 * @return
	 * @throws JSONException
	 */
	public CellAcquirer storeCell(List<JSONObject> appendTO, String coord) throws JSONException {
		if (coord.equals(StringRes.getString("mapfile.qdgenerator.input.terminator"))) {
			return createNextState();
		}
		else {
			JSONObject newcell = new JSONObject();
			newcell.put(
					StringRes.getString("mapfile.json.cells.alphanum"),
					CoordinatesConverter.prettifyAlphaNum(coord));
			newcell.put(
					StringRes.getString("mapfile.json.cells.tipo"),
					getTypeString());
			
			appendTO.add(newcell);
			return createMyState();
		}
	}
	
	public abstract String getPromptText();
	
	protected abstract String getTypeString();
	
	protected abstract CellAcquirer createNextState();
	
	protected abstract CellAcquirer createMyState();
}
