package it.escape.launcher.menucontroller;

public interface LauncherLocalSettings {
	
	public int getServerPort();

	public void setServerPort(int serverPort);

	public int getGameMasterTimeout();

	public void setGameMasterTimeout(int gameMasterTimeout);
	
	public boolean isStartInTextClient();

	public void setStartInTextClient(boolean startInTextClient);

	public boolean isStartInTextServer();

	public void setStartInTextServer(boolean startInTextServer);

	public String getDestinationServerAddress();

	public void setDestinationServerAddress(
			String destinationServerAddress);

}
