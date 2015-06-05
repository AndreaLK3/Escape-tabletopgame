package it.escape;

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
public class GlobalSettings {
	
	private static final Logger LOG = Logger.getLogger( GlobalSettings.class.getName() );
	private static boolean logSet = false;
	
	private static int ServerPort = 1331;
	
	private static int GameMasterTimeout = 60000;

	private static boolean StartInTextClient = false;
	
	private static boolean StartInTextServer = false;
	
	private static void setLog() {
		if (!logSet) {
			logSet = true;
			LogHelper.setDefaultOptions(LOG);
		}
	}
	
	public static int getServerPort() {
		return ServerPort;
	}

	public static void setServerPort(int serverPort) {
		setLog();
		LOG.finer("Setting ServerPort to " + serverPort + " (default: " + ServerPort + ")");
		ServerPort = serverPort;
	}

	public static int getGameMasterTimeout() {
		return GameMasterTimeout;
	}

	public static void setGameMasterTimeout(int gameMasterTimeout) {
		setLog();
		LOG.finer("Setting GameMasterTimeout to " + gameMasterTimeout + " (default: " + GameMasterTimeout + ")");
		GameMasterTimeout = gameMasterTimeout;
	}

	public static boolean isStartInTextClient() {
		return StartInTextClient;
	}

	public static void setStartInTextClient(boolean startInTextClient) {
		StartInTextClient = startInTextClient;
	}

	public static boolean isStartInTextServer() {
		return StartInTextServer;
	}

	public static void setStartInTextServer(boolean startInTextServer) {
		StartInTextServer = startInTextServer;
	}
	
	
}
