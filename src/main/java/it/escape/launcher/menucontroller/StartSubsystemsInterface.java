package it.escape.launcher.menucontroller;

public interface StartSubsystemsInterface {
	
	/*
	 * Note: the StartMenuInterface is required to stop the program
	 * when the textual components have finished their work.
	 */
	public void startTextSocketClient(StartMenuInterface startMenu);
	
	public void startGUISocketClient(StartMenuInterface startMenu);
	
	public void startGUIRMIClient(StartMenuInterface startMenu);
	
	public void startTextRMIClient(StartMenuInterface startMenu);
	
	public void startTextComboServer(StartMenuInterface startMenu);
	
	public void startTextRMIServer(StartMenuInterface startMenu);
	
	public void startGUISocketServer(StartMenuInterface startMenu);
	
	public void startGUIRMIServer(StartMenuInterface startMenu);
	
	public void startGUIComboServer(StartMenuInterface startMenu);
	
	public void startTextSocketServer(StartMenuInterface startMenu);
	
	
}
