package it.escape.tools;

import it.escape.tools.utils.LogHelper;

import java.util.logging.Logger;

/**
 * Class which holds *some* global options.
 * The intention is to hold all the options which
 * can be set via cli parameters, and make them
 * accessible everywhere else in the program.
 * When the program branches, a reference to this
 * class must be propagated to the various sub-branches.
 * @author michele
 *
 */
public class GlobalSettings {
	
	private final Logger LOG = Logger.getLogger( GlobalSettings.class.getName() );
	
	private int serverPort = 1337;
	
	private int gameMasterTimeout = 60000;
	
	private int gameTurnDuration = 60000;

	private boolean startInTextClient = false;
	
	private boolean startInTextServer = false;
	
	private boolean startInTextRMIMode = false;
	
	private boolean startInTextComboMode = false;
	
	private String destinationServerAddress = "localhost";
	
	private String[] mapRotation = {"Galilei"};
	
	public GlobalSettings(GlobalSettings old) {
		LogHelper.setDefaultOptions(LOG);
		serverPort = old.getServerPort();
		gameMasterTimeout = old.getGameMasterTimeout();
		startInTextClient = old.isStartInTextClient();
		startInTextServer = old.isStartInTextServer();
		destinationServerAddress = old.getDestinationServerAddress();
	}
	
	public GlobalSettings() {
		LogHelper.setDefaultOptions(LOG);
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int newServerPort) {
		LOG.finer("Setting ServerPort to " + newServerPort + " (default: " + serverPort + ")");
		serverPort = newServerPort;
	}

	public int getGameMasterTimeout() {
		return gameMasterTimeout;
	}

	public void setGameMasterTimeout(int newGameMasterTimeout) {
		LOG.finer("Setting GameMasterTimeout to " + newGameMasterTimeout + " (default: " + gameMasterTimeout + ")");
		gameMasterTimeout = newGameMasterTimeout;
	}

	public boolean isStartInTextClient() {
		return startInTextClient;
	}

	public void setStartInTextClient(boolean setStartInTextClient) {
		startInTextClient = setStartInTextClient;
	}

	public boolean isStartInTextServer() {
		return startInTextServer;
	}

	public void setStartInTextServer(boolean setStartInTextServer) {
		startInTextServer = setStartInTextServer;
	}

	public String getDestinationServerAddress() {
		return destinationServerAddress;
	}

	public void setDestinationServerAddress(String newDestinationServerAddress) {
		LOG.finer("Setting DestinationServerAddress to " + newDestinationServerAddress + " (default: " + destinationServerAddress + ")");
		destinationServerAddress = newDestinationServerAddress;
	}

	public boolean isStartInTextComboMode() {
		return startInTextComboMode;
	}

	public void setStartInTextComboMode(boolean setStartInTextComboMode) {
		startInTextComboMode = setStartInTextComboMode;
	}

	public boolean isStartInTextRMIMode() {
		return startInTextRMIMode;
	}

	public void setStartInTextRMIMode(boolean setStartInTextRMIMode) {
		startInTextRMIMode = setStartInTextRMIMode;
	}

	public int getGameTurnDuration() {
		return gameTurnDuration;
	}

	public void setGameTurnDuration(int newGameTurnDuration) {
		LOG.finer("Setting GameTurnDuration to " + newGameTurnDuration + " (default: " + gameTurnDuration + ")");
		gameTurnDuration = newGameTurnDuration;
	}

	public String[] getMaprotation() {
		return mapRotation;
	}

	public void setMaprotation(String[] newMapRotation) {
		StringBuilder rot = new StringBuilder();
		rot.append("Setting maprotation to:\n");
		for (String name : newMapRotation) {
			rot.append(name + "\n");
		}
		rot.append("Default maprotation was:\n");
		for (String name : this.mapRotation) {
			rot.append(name + "\n");
		}
		LOG.finer(rot.toString());
		this.mapRotation = newMapRotation;
	}
	
	
}
