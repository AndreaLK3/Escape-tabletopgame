package it.escape.core.server.model.game.gamemap.qdgenerator;

import java.io.FileNotFoundException;

import it.escape.tools.utils.FilesHelper;

public class qdgenMain {
	public static void main(String[] args) {
		CommandLine cmd = new CommandLine();
		System.out.println("Quick and Dirty Map Generator");
		cmd.acquire();
		try {
			FilesHelper.saveToFile("prova_save.json", cmd.toString());
			System.out.println("File saved");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}
