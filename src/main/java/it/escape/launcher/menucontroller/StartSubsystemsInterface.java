package it.escape.launcher.menucontroller;

// TODO: keep the methods for starting rmi and socket _indipendently_
public interface StartSubsystemsInterface {
	
	/*
	 * Note: the StartMenuInterface is required to stop the program
	 * when the textual components have finished their work.
	 */
	public void startTextSocketClient(StartMenuInterface startMenu);
	
	public void startGUISocketClient(StartMenuInterface startMenu);
	
	public void startTextComboServer(StartMenuInterface startMenu);
	
	public void startTextRMIServer(StartMenuInterface startMenu);
	
	public void startTextSocketServer(StartMenuInterface startMenu);
	
	//public void startGUIRMIClient(StartMenuInterface startMenu);
	
}
