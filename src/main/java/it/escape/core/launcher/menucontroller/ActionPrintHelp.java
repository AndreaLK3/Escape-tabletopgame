package it.escape.core.launcher.menucontroller;

import it.escape.tools.utils.HelpFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPrintHelp implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		HelpFile.extractHelpFile();
		HelpFile.displayInBrowser();
	}

}
