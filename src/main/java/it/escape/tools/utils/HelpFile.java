package it.escape.tools.utils;

import it.escape.core.launcher.menucontroller.ActionPrintHelp;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpFile {
	
	private static final Logger LOGGER = Logger.getLogger( ActionPrintHelp.class.getName() );
	
	private static final String FILENAME = "EFTAIOS_help_en.html";
	
	public static void extractHelpFile() {
		LogHelper.setDefaultOptions(LOGGER);
		try {
			String content = FilesHelper.streamToString(
					FilesHelper.getResourceFile("resources/help/" + FILENAME));
			FilesHelper.saveToFile(FILENAME, content);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IO error", e);
		}
	}
	
	public static void displayInBrowser() {
		File htmlIndex = new File(FILENAME);
		try {
			Desktop.getDesktop().browse(htmlIndex.toURI());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IO error", e);
		}
	}
}
