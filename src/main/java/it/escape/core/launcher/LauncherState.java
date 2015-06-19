package it.escape.core.launcher;

import it.escape.core.launcher.menucontroller.LauncherStateInterface;
import it.escape.tools.strings.StringRes;

public class LauncherState implements LauncherStateInterface {

	private String netMode = StringRes.getString("launcher.option.netmode.socket");
	
	private String uiMode = StringRes.getString("launcher.option.experience.graphical");

	public String getNetMode() {
		return netMode;
	}

	public void setNetMode(String netMode) {
		this.netMode = netMode;
	}

	public String getUiMode() {
		return uiMode;
	}

	public void setUiMode(String uiMode) {
		this.uiMode = uiMode;
	}
	
}
