package it.escape.launcher.menucontroller;

import it.escape.launcher.GlobalSettings;

public interface StartMenuInterface {
	
	public void tbiMessage();
	
	public void closeMenu();
	
	public void closeProgram();
	
	public GlobalSettings getLocalSettings();
}
