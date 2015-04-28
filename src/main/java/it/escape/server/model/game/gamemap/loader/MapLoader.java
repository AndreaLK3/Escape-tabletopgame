package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.gamemap.Cella;
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

public class MapLoader implements Iterable<Cella> {
	
	private final String rawData;
	
	private String mapName;
	
	private Position2D mapSize;
	
	private final List<Cella> listaCelle;
	
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
			
			JSONArray cellArr = root.getJSONArray(StringRes.getString("mapfile.json.cells.arrname"));
			for (int i = 0; i < cellArr.length(); i++) {
				listaCelle.add( (new CellGenerator( cellArr.getJSONObject(i) )).convert() );
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
		listaCelle = new ArrayList<Cella>();
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
