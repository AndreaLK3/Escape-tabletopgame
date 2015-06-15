package it.escape.launcher.menucontroller;

public interface StartSubsystemsInterface {
	
	/*
	 * Note: the StartMenuInterface is required to stop the program
	 * when the textual components have finished their work.
	 */
	public void startTextSocketClient(StartMenuInterface startMenu);
	
	public void startTextServer(StartMenuInterface startMenu);
	
	public void startGUISocketClient(StartMenuInterface startMenu);
	
	//TODO: public void public void startTextRMIClient(StartMenuInterface startMenu),
	//public void startGUIRMIClient(StartMenuInterface startMenu);
	
}
