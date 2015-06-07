package it.escape.launcher.menucontroller;

import it.escape.strings.StringRes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionStartServer implements ActionListener {
	
	private LauncherStateInterface launcherState;
	
	private StartMenuInterface startMenu;

	public ActionStartServer(LauncherStateInterface launcherState, StartMenuInterface startMenu) {
		this.launcherState = launcherState;
		this.startMenu = startMenu;
	}

	public void actionPerformed(ActionEvent e) {
		if (StringRes.getString("launcher.button.server").equals(e.getActionCommand())) {
			chooseServer();
		}
	}
	
	private void chooseServer() {
		String ui = launcherState.getUiMode();
		String net = launcherState.getNetMode();
		if (ui.equals(StringRes.getString("launcher.option.experience.textual"))) {
			if (net.equals(StringRes.getString("launcher.option.netmode.socket"))) {
				startMenu.getStarter().startTextSocketServer(startMenu);
				startMenu.closeMenu();
			} else if (net.equals(StringRes.getString("launcher.option.netmode.RMI"))) {
				startMenu.tbiMessage();
			} else {
				startMenu.tbiMessage();
			}
			
		} else if (ui.equals(StringRes.getString("launcher.option.experience.graphical"))) {
			startMenu.tbiMessage();
		} else {
			startMenu.tbiMessage();
		}
	}
}
