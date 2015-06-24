package it.escape.core.launcher.menucontroller;

import it.escape.tools.utils.FilesHelper;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionPrintHelp implements ActionListener {
	
	private static final Logger LOGGER = Logger.getLogger( ActionPrintHelp.class.getName() );
	
	private static final String FILENAME = "EFTAIOS_help_en.html";
	
	private void extractHelpFile() {
		try {
			String content = FilesHelper.streamToString(
					FilesHelper.getResourceFile("resources/help/" + FILENAME));
			FilesHelper.saveToFile(FILENAME, content);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IO error", e);
		}
	}
	
	private void displayInBrowser() {
		File htmlIndex = new File(FILENAME);
		try {
			Desktop.getDesktop().browse(htmlIndex.toURI());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IO error", e);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		extractHelpFile();
		displayInBrowser();
	}

}
