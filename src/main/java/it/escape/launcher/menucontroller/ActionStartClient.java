package it.escape.launcher.menucontroller;

import it.escape.client.ClientInitializerCLI;
import it.escape.launcher.LauncherState;
import it.escape.strings.StringRes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionStartClient implements ActionListener {
	
	private LauncherState launcherState;
	
	private StartMenuInterface startMenu;

	public ActionStartClient(LauncherState launcherState, StartMenuInterface startMenu) {
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
				startSockTextClient();
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

	private void startSockTextClient() {
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						ClientInitializerCLI.start();
						startMenu.closeProgram();
					}}).start();
		startMenu.closeMenu();
	}
}
