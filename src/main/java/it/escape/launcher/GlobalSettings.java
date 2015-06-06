package it.escape.launcher;

import it.escape.client.ClientLocalSettings;
import it.escape.launcher.menucontroller.LauncherLocalSettings;
import it.escape.server.ServerLocalSettings;
import it.escape.utils.LogHelper;

import java.util.logging.Logger;

/**
 * Static class which holds *some* global options.
 * The intention is to hold all the options which
 * can be set via cli parameters, and make them
 * accessible everywhere else in the program
 * @author michele
 *
 */
public class GlobalSettings implements ServerLocalSettings, ClientLocalSettings, LauncherLocalSettings {
	
	private final Logger LOG = Logger.getLogger( GlobalSettings.class.getName() );
	
	private int ServerPort = 1337;
	
	private int GameMasterTimeout = 60000;

	private boolean StartInTextClient = false;
	
	private boolean StartInTextServer = false;
	
	private String DestinationServerAddress = "localhost";
	
	public GlobalSettings(GlobalSettings old) {
		LogHelper.setDefaultOptions(LOG);
		ServerPort = old.getServerPort();
		GameMasterTimeout = old.getGameMasterTimeout();
		StartInTextClient = old.isStartInTextClient();
		StartInTextServer = old.isStartInTextServer();
		DestinationServerAddress = old.getDestinationServerAddress();
	}
	
	public GlobalSettings() {
		LogHelper.setDefaultOptions(LOG);
	}

	public int getServerPort() {
		return ServerPort;
	}

	public void setServerPort(int serverPort) {
		LOG.finer("Setting ServerPort to " + serverPort + " (default: " + ServerPort + ")");
		ServerPort = serverPort;
	}

	public int getGameMasterTimeout() {
		return GameMasterTimeout;
	}

	public void setGameMasterTimeout(int gameMasterTimeout) {
		LOG.finer("Setting GameMasterTimeout to " + gameMasterTimeout + " (default: " + GameMasterTimeout + ")");
		GameMasterTimeout = gameMasterTimeout;
	}

	public boolean isStartInTextClient() {
		return StartInTextClient;
	}

	public void setStartInTextClient(boolean startInTextClient) {
		StartInTextClient = startInTextClient;
	}

	public boolean isStartInTextServer() {
		return StartInTextServer;
	}

	public void setStartInTextServer(boolean startInTextServer) {
		StartInTextServer = startInTextServer;
	}

	public String getDestinationServerAddress() {
		return DestinationServerAddress;
	}

	public void setDestinationServerAddress(
			String destinationServerAddress) {
		LOG.finer("Setting DestinationServerAddress to " + destinationServerAddress + " (default: " + DestinationServerAddress + ")");
		DestinationServerAddress = destinationServerAddress;
	}
	
	
}
