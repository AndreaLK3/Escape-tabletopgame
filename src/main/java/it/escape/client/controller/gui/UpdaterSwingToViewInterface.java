package it.escape.client.controller.gui;

import it.escape.client.model.ModelForGUI;

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
	
	public void relayYesNoDialog(String question,String option1,String option2);
	
	public void notifyUser(String message);
	
	public void relayObjectCard();
	
	public void addNoiseToMap(String location);
	
	public void clearNoisesFromMap();
	
	public void notifyNewCard(String cardName);
	
	public void addOtherPlayerToMap(String location, String name);
	
	public void removeOtherPlayerFromMap(String name);
	
	public void clearOtherPlayersFromMap();
	
	public void focusOnLocation(String coord, int waitBefore);
	
	public void spawnVictoryRecap(ModelForGUI model);
	
}
