package it.escape.server.view.rmispecific;

import it.escape.client.connection.rmi.ClientRemoteInterface;
import it.escape.server.Master;
import it.escape.server.ServerLocalSettings;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.model.AnnouncerRMIBroadcast;
import it.escape.server.model.SuperAnnouncer;
import it.escape.server.model.Announcer;
import it.escape.server.model.game.players.Player;
import it.escape.server.view.MessagingChannelInterface;
import it.escape.server.view.MessagingChannelRMI;
import it.escape.strings.StringRes;
import it.escape.utils.FilesHelper;
import it.escape.utils.LogHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * RMI server implementation. It exposes methods to be called by specific
 * clients; a client will identify itself when calling such methods.
 * @author michele
 *
 */
public class ServerRMI implements ServerRemoteInterface {
	
	protected static final Logger LOGGER = Logger.getLogger( ServerRMI.class.getName() );
	
	public static final int REGISRTY_PORT = 1099;

	// it's more useful to list the MessagingChannelRMI, which match 1:1 to the active clients
	private List<MessagingChannelRMI> clientsList;
	
	private ServerLocalSettings locals;
	
	private MessagingChannelRMI findChannel(ClientRemoteInterface client) {
		for (MessagingChannelRMI c : clientsList) {
			try {
				if (c.getClient().getID() == client.getID()) {
					return c;
				}
			} catch (RemoteException e) {
				LOGGER.warning("Could not ask the client for its ID");
			}
		}
		return null;
	}
	
	/**Basically does the same things that Connection used to do upon establishing:
	 * 1)binds a MessagingChannel to the client connection(here, ClientRemoteInterface)
	 * 2)add a Client to the list of clients that use RMI
	 * 3)creates the appropriate UserMessagesReporter (UMR_RMI)
	 * 4)invokes: Master.newPlayerHasConnected(channel, locals);
	 * 5)sets up the Client as a subscriber of the appropriate Announcer(here, AnnouncerRMIBroadcast) */
	@Override
	public void registerClient(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface channel = new MessagingChannelRMI(client, this);
		clientsList.add((MessagingChannelRMI) channel);
		client.setID(((MessagingChannelRMI) channel).getID());
		UserMessagesReporter.createUMR(channel);
		Master.newPlayerHasConnected(channel, locals);
		SuperAnnouncer superAnnouncer = (SuperAnnouncer) UserMessagesReporter.getReporterInstance(channel).getAnnouncer();
		AnnouncerRMIBroadcast announcer = superAnnouncer.getRMIAnnouncer();
		announcer.subscribe(client);
		try {
			client.setWholeMOTD(StringRes.getString("messaging.motd.start") + "\n" + 
						FilesHelper.streamToString(
								FilesHelper.getResourceFile("resources/MOTD.txt")
								) + "\n" +
						StringRes.getString("messaging.motd.end"));
		} catch (IOException e) {
			LOGGER.warning(StringRes.getString("view.connection.cantWelcome"));
		}
		LOGGER.info("Client " + client.toString() + " registered");
	}

	@Override
	public void unregisterClient(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI del = findChannel(client);
		if (del != null) {
			clientsList.remove(del);
			Announcer clientRMIAnnouncer =  UserMessagesReporter.getReporterInstance(del).getAnnouncer();
			if (clientRMIAnnouncer instanceof AnnouncerRMIBroadcast) {
				((AnnouncerRMIBroadcast)clientRMIAnnouncer).unSubscribe(client);
			}
			else {
				LOGGER.warning("Could not unsubscribe client properly from AnnouncerRMIBroadcast");
			}
			
		}
	}

	private AnnouncerRMIBroadcast Announcer(AnnouncerRMIBroadcast announcer) {
		// TODO: What is this?
		return null;
	}

	@Override
	public void rename(String message, ClientRemoteInterface client) throws RemoteException {
		// get the player reference from UMR
		// get the gamemaster
		// call the rename procedure in gamemaster
		// all the other methods work in a similar way
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			Master.getGameMasterOfPlayer(player).renamePlayer(player, message);
		} else
			LOGGER.warning("MessagingChannelInterface is missing");
		LOGGER.info("Client " + client.toString() + " renamed himself");
	}

	@Override
	public void globalChat(String message, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			Master.getGameMasterOfPlayer(player).globalChat(player, message);
		} else
			LOGGER.warning("MessagingChannelInterface is missing");
		LOGGER.info("Client " + client.toString() + " sent a chat message");
	}

	@Override
	public void whoAmI(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			((MessagingChannelRMI) pla).getClient().renameMyself(player.getName());
		} else
			LOGGER.warning("MessagingChannelInterface is missing");
		LOGGER.info("Client " + client.toString() + " sent whoami");
	}

	@Override
	public void whereAmI(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			String myPos = Master.getGameMasterOfPlayer(player).getPlayerPosition(player);
			((MessagingChannelRMI) pla).getClient().setMyPosition(myPos);
		} else
			LOGGER.warning("MessagingChannelInterface is missing");
		LOGGER.info("Client " + client.toString() + " sent whereami");
	}

	@Override
	public void setAnswer(String answer, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI chan = findChannel(client);
		if (chan != null) {
			chan.setAnswer(answer);
		} else
			LOGGER.warning("MessagingChannelInterface is missing");
		LOGGER.info("Client " + client.toString() + " sent answer: " + answer);
	}
	
	/**This method sets up the Registry and creates and exposes the Server Remote Object;
	 *  after the Server invoked this, the clients using RMI will be able to invoke functions.
	 * @throws RemoteException 
	 * @throws MalformedURLException */
	public static void initializer(ServerLocalSettings locals) throws RemoteException, MalformedURLException {
		LogHelper.setDefaultOptions(LOGGER);
		LOGGER.info("Creating local registry");
		LocateRegistry.createRegistry(REGISRTY_PORT);
		LOGGER.info("Created RMI registry on port " + REGISRTY_PORT);
		ServerRemoteInterface server = new ServerRMI(locals);
		UnicastRemoteObject.exportObject(server, locals.getServerPort());
		LOGGER.info("Exported server interface");
		Naming.rebind("//localhost/Server", server);
		LOGGER.info("Server interface bound to name: \"" + "//localhost/Server" + "\"");
	}
	
	/**Constructor for the object*/
	public ServerRMI(ServerLocalSettings locals) {
		this.locals = locals;
		clientsList = new ArrayList<MessagingChannelRMI>();
	}

}
