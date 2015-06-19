package it.escape.core.launcher.menucontroller;

import it.escape.tools.GlobalSettings;


public interface StartMenuInterface {
	
	public void tbiMessage();
	
	public void closeMenu();
	
	public void closeProgram();
	
	public GlobalSettings getLocalSettings();
	
	public StartSubsystemsInterface getStarter();
}
