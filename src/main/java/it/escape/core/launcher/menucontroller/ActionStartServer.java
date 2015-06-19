package it.escape.core.launcher.menucontroller;

import it.escape.core.server.swinglogviewer.Monitor;
import it.escape.tools.strings.StringRes;

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
				startMenu.getStarter().startTextRMIServer(startMenu);
				startMenu.closeMenu();
			} else if (net.equals(StringRes.getString("launcher.option.netmode.combo"))){
				startMenu.getStarter().startTextComboServer(startMenu);
				startMenu.closeMenu();
			} else {
				startMenu.tbiMessage();
			}
			
		} else if (ui.equals(StringRes.getString("launcher.option.experience.graphical"))) {
			if (net.equals(StringRes.getString("launcher.option.netmode.socket"))) {
				startMenu.getStarter().startGUISocketServer(startMenu);
				startMenu.closeMenu();
			} else if (net.equals(StringRes.getString("launcher.option.netmode.RMI"))) {
				startMenu.getStarter().startGUIRMIServer(startMenu);
				startMenu.closeMenu();
			} else if (net.equals(StringRes.getString("launcher.option.netmode.combo"))){
				startMenu.getStarter().startGUIComboServer(startMenu);
				startMenu.closeMenu();
			} else {
				startMenu.tbiMessage();
			}
		} else {
			startMenu.tbiMessage();
		}
	}
}
