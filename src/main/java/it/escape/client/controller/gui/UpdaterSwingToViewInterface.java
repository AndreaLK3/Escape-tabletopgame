package it.escape.client.controller.gui;

/**
 * The updater will use this class to modify the view (swing)
 * @author michele
 *
 */
public interface UpdaterSwingToViewInterface {
	
	public void setGameMap(String name);
	
	public void displayServerMOTD(String motd);
	
	public void setTurnStatusString(String status);
	
	public void newChatMessage(String username, String message);
	
	public void discoverMyName();
	
	public void bindPositionSender();
	
	public void unbindPositionSender(ClickSendPositionListener listener);

	public void relayYesNoDialog(String question);
	
	public void notifyUser(String message);
	
	public void relayObjectCard();
	
}
