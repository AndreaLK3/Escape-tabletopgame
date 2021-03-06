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
	
	private int ServerPort = 1337;
	
	private int GameMasterTimeout = 60000;
	
	private int GameTurnDuration = 60000;

	private boolean StartInTextClient = false;
	
	private boolean StartInTextServer = false;
	
	private boolean StartInTextRMIMode = false;
	
	private boolean StartInTextComboMode = false;
	
	private boolean ImmediatelySaveHTMLHelp = false;
	
	private String DestinationServerAddress = "localhost";
	
	private String[] Maprotation = {"Galilei"};
	
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

	public boolean isStartInTextComboMode() {
		return StartInTextComboMode;
	}

	public void setStartInTextComboMode(boolean startInTextComboMode) {
		StartInTextComboMode = startInTextComboMode;
	}

	public boolean isStartInTextRMIMode() {
		return StartInTextRMIMode;
	}

	public void setStartInTextRMIMode(boolean startInTextRMIMode) {
		StartInTextRMIMode = startInTextRMIMode;
	}

	public int getGameTurnDuration() {
		return GameTurnDuration;
	}

	public void setGameTurnDuration(int gameTurnDuration) {
		LOG.finer("Setting GameTurnDuration to " + gameTurnDuration + " (default: " + GameTurnDuration + ")");
		GameTurnDuration = gameTurnDuration;
	}

	public String[] getMaprotation() {
		return Maprotation;
	}

	public void setMaprotation(String[] maprotation) {
		StringBuilder rot = new StringBuilder();
		rot.append("Setting maprotation to:\n");
		for (String name : maprotation) {
			rot.append(name + "\n");
		}
		rot.append("Default maprotation was:\n");
		for (String name : this.Maprotation) {
			rot.append(name + "\n");
		}
		LOG.finer(rot.toString());
		this.Maprotation = maprotation;
	}

	public boolean isImmediatelySaveHTMLHelp() {
		return ImmediatelySaveHTMLHelp;
	}

	public void setImmediatelySaveHTMLHelp(boolean immediatelyOpenHTMLHelp) {
		ImmediatelySaveHTMLHelp = immediatelyOpenHTMLHelp;
	}
	
	
}
