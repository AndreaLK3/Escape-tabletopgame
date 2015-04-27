package it.escape.server.model.game.gamemap;
import it.escape.server.model.game.gamemap.positioning.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * <IN COSTRUZIONE>
 * Oggetto con lo scopo di caricare in memoria un file "mappa", l'oggetto funge da:
 * 1) iteratore sulle celle lette da file
 * 2) restituisce il nome e il range della mappa
 * La codifica è ancora da stabilire, consiglio JSON
 * @author michele
 *
 */
public class MapLoader implements Iterator<Cella> {
	
	// conterrà i caratteri 'grezzi' così come sono nel file
	private String rawData;

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	public Cella next() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @return nome della mappa letta da file
	 */
	public String getMapName() {
		return null;
	}
	/**
	 * 
	 * @return punto di coordinate (larghezza mappa, ampiezza mappa)
	 */
	public Position2D getMapSize() {
		return null;
	}
	
	/**
	 * to be implemented.
	 * decodifica   dati grezzi -> oggetti dati interni
	 * meglio usare una ulteriore classe accessoria per fare questo
	 */
	private void decode() {
		
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

	public MapLoader(InputStream ingresso) throws IOException {
		rawData = streamToString(ingresso);
		decode();
	}
	
}
