package it.escape.launcher.menucontroller;


public interface StartMenuInterface {
	
	public void tbiMessage();
	
	public void closeMenu();
	
	public void closeProgram();
	
	public LauncherLocalSettings getLocalSettings();
	
	public StartSubsystemsInterface getStarter();
}
