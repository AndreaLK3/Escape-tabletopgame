package it.escape.server.model.game.gamemap.qdgenerator;

import it.escape.server.model.game.exceptions.BadCoordinatesException;
import it.escape.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.strings.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Auxiliary class used to manually input a new map into the game.
 * The class will export the map as a JSON-encoded string
 * @author michele
 *
 */
public class CommandLine {
	
	private static final int INDENT_FACTOR = 2;

	private JSONObject constructed;
	
	private Scanner scanner;

	public CommandLine() {
		this.scanner = new Scanner(System.in);
		this.constructed = new JSONObject();
	}
	
	private String promptUppercase(String message) {
		System.out.println(message);
		return scanner.next().toUpperCase();
	}
	
	private String prompt(String message) {
		System.out.println(message);
		return scanner.next();
	}
	
	/**
	 * creates the JSON object containing the global map data
	 * @return
	 * @throws JSONException
	 * @throws BadCoordinatesException
	 */
	private JSONObject addGlobalData() throws JSONException, BadCoordinatesException {
		JSONObject globals = new JSONObject();
		globals.put(
				StringRes.getString("mapfile.json.info.name"), 
				prompt(StringRes.getString("mapfile.qdgenerator.input.mapname")));
		Position2D size = CoordinatesConverter.fromAlphaNumToOddq(
				promptUppercase(StringRes.getString("mapfile.qdgenerator.input.mapsize")));
		globals.put(
				StringRes.getString("mapfile.json.info.width"), 
				size.getX());
		globals.put(
				StringRes.getString("mapfile.json.info.height"), 
				size.getY());
		return globals;
	}
	
	/**
	 * creates an array on JSON objects representing the individual cells
	 * CellAcquirer implements the state pattern
	 * @return
	 * @throws JSONException
	 */
	private List<JSONObject> addCellsData() throws JSONException {
		List<JSONObject> ret = new ArrayList<JSONObject>();
		CellAcquirer current = new SafeCellAcquirer();
		while (current != null) {
			current = current.storeCell(
					ret, 
					promptUppercase(current.getPromptText()));
		}
		return ret;
	}
	
	/**
	 * Generate the JSON string representing the map.
	 * Command-line interaction with the user is mandatory.
	 */
	public void acquire() {
		try {
			constructed.put(
					StringRes.getString("mapfile.json.mapInfo"), 
					addGlobalData());
			constructed.put(
					StringRes.getString("mapfile.json.cells.arrname"), 
					addCellsData()
					);
		} catch (JSONException e) {
			System.out.println("bad json");
		} catch (BadCoordinatesException e) {
			System.out.println("bad coordinates");
		}
	}
	
	@Override
	public String toString() {
		try {
			return constructed.toString(INDENT_FACTOR);
		} catch (JSONException e) {
			return "JSON error";
		}
	}
}
