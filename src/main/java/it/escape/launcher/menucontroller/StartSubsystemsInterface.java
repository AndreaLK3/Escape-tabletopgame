package it.escape.launcher.menucontroller;

public interface StartSubsystemsInterface {
	
	/*
	 * Note: the StartMenuInterface is required to stop the program
	 * when the textual components have finished their work.
	 * The gui components do not need it, as they can stop it
	 * simply by pressing quit
	 */
	public void startTextSocketClient(StartMenuInterface startMenu);
	
	public void startTextSocketServer(StartMenuInterface startMenu);
	
	public void startGUISocketClient();
}
