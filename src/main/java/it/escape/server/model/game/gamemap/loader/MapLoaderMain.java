package it.escape.server.model.game.gamemap.loader;

import it.escape.server.model.game.exceptions.BadJsonFileException;
import it.escape.server.model.game.gamemap.Cell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MapLoaderMain {

	
	public static void main(String[] args) {
	
		try {
			InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("test_map.json");
			MapLoader loader = new MapLoader(file);
			
			for (Cell c : loader) {
				System.out.println(c.toString());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (BadJsonFileException e) {
			System.out.println("bad json file");
		} catch (IOException e) {
			System.out.println("file not readable");
		}
	}
}
