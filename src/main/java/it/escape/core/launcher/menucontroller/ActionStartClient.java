package it.escape.core.launcher.menucontroller;

import it.escape.tools.strings.StringRes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionStartClient implements ActionListener {
	
	private LauncherStateInterface launcherState;
	
	private StartMenuInterface startMenu;

	public ActionStartClient(LauncherStateInterface launcherState, StartMenuInterface startMenu) {
		this.launcherState = launcherState;
		this.startMenu = startMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (StringRes.getString("launcher.button.client").equals(e.getActionCommand())) {
			chooseClient();
		}
	}
	
	private void chooseClient() {
		String ui = launcherState.getUiMode();
		String net = launcherState.getNetMode();
		if (ui.equals(StringRes.getString("launcher.option.experience.textual"))) {
			if (net.equals(StringRes.getString("launcher.option.netmode.socket"))) {
				startMenu.getStarter().startTextSocketClient(startMenu);
				startMenu.closeMenu();
			} else if (net.equals(StringRes.getString("launcher.option.netmode.RMI"))) {
				startMenu.getStarter().startTextRMIClient(startMenu);
				startMenu.closeMenu();
			} else {
				startMenu.tbiMessage();
			}
			
		} else if (ui.equals(StringRes.getString("launcher.option.experience.graphical"))) {
			if (net.equals(StringRes.getString("launcher.option.netmode.socket"))) {
				startMenu.getStarter().startGUISocketClient(startMenu);
				startMenu.closeMenu();
			} else if (net.equals(StringRes.getString("launcher.option.netmode.RMI"))) {
				startMenu.getStarter().startGUIRMIClient(startMenu);
				startMenu.closeMenu();
			} else {
				startMenu.tbiMessage();
			}
		} else {
			startMenu.tbiMessage();
		}
	}

}
