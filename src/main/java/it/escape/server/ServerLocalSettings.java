package it.escape.server;

public interface ServerLocalSettings {
	public int getServerPort();

	public void setServerPort(int serverPort);

	public int getGameMasterTimeout();

	public boolean isStartInTextClient();

	public boolean isStartInTextServer();

	public String getDestinationServerAddress();

	public int getGameTurnDuration();

	public String[] getMaprotation();
}
