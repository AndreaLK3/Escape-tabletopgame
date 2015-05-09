package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.gamemap.Cell;
import it.escape.server.model.game.gamemap.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.positioning.Position2D;
import it.escape.strings.StringRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * MapLoader reads a JSON-encoded map from an InputStream.
 * It implements getters for the main map parameters as well as
 * an iterator over the loaded map cells.
 * @author michele
 *
 */
public class MapLoader implements Iterable<Cell> {
	
	private final String rawData;
	
	private String mapName;
	
	private Position2D mapSize;
	
	private final List<Cell> listaCelle;
	
	/**
	 * 
	 * @return nome della mappa letta da file
	 */
	public String getMapName() {
		return mapName;
	}
	/**
	 * 
	 * @return punto di coordinate: (larghezza mappa, ampiezza mappa)
	 */
	public Position2D getMapSize() {
		return mapSize;
	}
	
	/**
	 * decodifica   dati grezzi -> oggetti dati interni
	 * @throws BadJsonFileException 
	 */
	private void decode() throws BadJsonFileException {
		try {
			JSONObject root = new JSONObject(rawData);
			
			JSONObject mapInfo = root.getJSONObject(StringRes.getString("mapfile.json.mapInfo"));
			mapName = mapInfo.getString(StringRes.getString("mapfile.json.info.name"));
			mapSize = new Position2D(mapInfo.getInt(StringRes.getString("mapfile.json.info.width"))
					, mapInfo.getInt(StringRes.getString("mapfile.json.info.height")));
			
			JSONArray Cellrr = root.getJSONArray(StringRes.getString("mapfile.json.cells.arrname"));
			System.out.println(Cellrr);
			for (int i = 0; i < Cellrr.length(); i++) {
				listaCelle.add( (new CellGenerator( Cellrr.getJSONObject(i) )).convert() );
			}
			
		} catch (JSONException e) {
			throw new BadJsonFileException();
		}
	}
	
	/**
	 * converte un inputstream in una stringa
	 * 
	 * @param ingresso
	 * @return
	 * @throws IOException
	 */
	private String streamToString(InputStream ingresso) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(ingresso));
		StringBuilder ret = new StringBuilder();
		String line;
		
		while ((line = reader.readLine()) != null) {
	        ret.append(line);
	    }
		
		return ret.toString();
	}

	public MapLoader(InputStream ingresso) throws BadJsonFileException, IOException {
		listaCelle = new ArrayList<Cell>();
		rawData = streamToString(ingresso);
		decode();
	}
	/**
	 * restituisce un iteratore sulle celle caricate da file
	 */
	public MapIterator iterator() {
		return new MapIterator(listaCelle);
	}

}
