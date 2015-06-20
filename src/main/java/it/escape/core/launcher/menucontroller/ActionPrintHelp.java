package it.escape.core.launcher.menucontroller;

import it.escape.tools.utils.FilesHelper;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ActionPrintHelp implements ActionListener {
	
	private static final String filename = "EFTAIOS_help_en.html";
	
	private void extractHelpFile() {
		try {
			String content = FilesHelper.streamToString(
					FilesHelper.getResourceFile("resources/help/" + filename));
			FilesHelper.saveToFile(filename, content);
		} catch (IOException e) {
		}
	}
	
	private void displayInBrowser() {
		File htmlIndex = new File(filename);
		try {
			Desktop.getDesktop().browse(htmlIndex.toURI());
		} catch (IOException e) {
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		extractHelpFile();
		displayInBrowser();
	}

}
