package it.escape.client.controller.gui;

/**
 * The updater will use this class to modify the view (swing)
 * @author michele
 *
 */
public interface UpdaterSwingToDisplayerInterface {
	
	public void setGameMap(String name);
	
	public void displayServerMOTD(String motd);
	
	public void setTurnStatusString(String status);
	
	public void newChatMessage(String username, String message);
	
}
