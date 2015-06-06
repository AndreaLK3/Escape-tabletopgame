package it.escape.launcher.menucontroller;

import it.escape.strings.StringRes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionQuit implements ActionListener {
	
	private StartMenuInterface startMenu;

	public ActionQuit(StartMenuInterface startMenu) {
		this.startMenu = startMenu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (StringRes.getString("launcher.button.quit").equals(e.getActionCommand())) {
			startMenu.closeProgram();
		}
	}

}
