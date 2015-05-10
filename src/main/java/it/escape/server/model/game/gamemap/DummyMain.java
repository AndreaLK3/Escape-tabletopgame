package it.escape.server.model.game.gamemap;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.loader.MapLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DummyMain {

	public static void main(String[] args) {
		try {
			InputStream fromfile = Thread.currentThread().getContextClassLoader().getResourceAsStream("test_map.json");
			MapLoader loader = new MapLoader(fromfile);
			
			System.out.println("map name: " + loader.getMapName());
			System.out.println("map range: " + loader.getMapSize().toString());
			System.out.println("Celle:");
			for (Cell c : loader) {
				System.out.println(c.toString());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File mappa inesistente.");
		} catch (IOException e) {
			System.out.println("File mappa non leggibile.");
		} catch (BadJsonFileException e) {
			System.out.println("JSON malformato.");
		}

	}

}
