package it.escape.core.server.view.rmispecific;

import it.escape.core.client.connection.rmi.ClientRemoteInterface;
import it.escape.core.server.Master;
import it.escape.core.server.ServerShutdownHook;
import it.escape.core.server.controller.UserMessagesReporter;
import it.escape.core.server.model.AnnouncerRMIBroadcast;
import it.escape.core.server.model.SuperAnnouncer;
import it.escape.core.server.model.game.players.Player;
import it.escape.core.server.view.MessagingChannelInterface;
import it.escape.core.server.view.MessagingChannelRMI;
import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.FilesHelper;
import it.escape.tools.utils.LogHelper;
import it.escape.tools.utils.SingleShutdownHook;

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
 */
public class ServerRMICore implements ServerRemoteInterface {
	
	private static final Logger LOGGER = Logger.getLogger( ServerRMICore.class.getName() );
	
	public static final int REGISRTY_PORT = 1099;

	// it's more useful to list the MessagingChannelRMI, which match 1:1 to the active clients
	private List<MessagingChannelRMI> clientsList;
	
	private GlobalSettings locals;
	
	private PingSender pinger;
	
	private static ServerRMICore serverInstance = null;
	
	
	/**Constructor for the object; it's private, since the object is a singleton*/
	private ServerRMICore(GlobalSettings locals) {
		this.locals = locals;
		clientsList = new ArrayList<MessagingChannelRMI>();
	}
	

	public static ServerRMICore getServerInstance(GlobalSettings locals) {
		if (serverInstance == null) {
			serverInstance = new ServerRMICore(locals);
		}
		return serverInstance;
	}
	
	
	
	/**Finds the MessagingChannelRMI corresponding to a ClientRemote object*/
	public MessagingChannelRMI findChannel(ClientRemoteInterface client) {
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
	
	public void clientLost(MessagingChannelRMI channel) {
		unregisterChannel(channel);
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
		try {
			client.setWholeMOTD(FilesHelper.streamToString(
					FilesHelper.getResourceFile("resources/MOTD.txt")));
		} catch (IOException e) {
			LOGGER.warning(StringRes.getString("view.connection.cantWelcome"));
		}
		UserMessagesReporter.createUMR(channel);
		Master.newPlayerHasConnected(channel, locals);
		SuperAnnouncer superAnnouncer = (SuperAnnouncer) UserMessagesReporter.getReporterInstance(channel).getAnnouncer();
		AnnouncerRMIBroadcast announcer = superAnnouncer.getRMIAnnouncer();
		announcer.subscribe(client);
		LOGGER.info("Client " + client.toString() + " registered");
		tryStartPinging();
	}
	
	private void unregisterChannel(MessagingChannelRMI channel) {
		clientsList.remove(channel);
		tryStopPinging();
		SuperAnnouncer clientSuperAnnouncer =  (SuperAnnouncer) UserMessagesReporter.getReporterInstance(channel).getAnnouncer();
		AnnouncerRMIBroadcast clientRMIAnnouncer = clientSuperAnnouncer.getRMIAnnouncer();
		if (clientRMIAnnouncer != null) {
			clientRMIAnnouncer.unSubscribe(channel.getClient());
		}
		else {
			LOGGER.warning("Could not unsubscribe client properly from AnnouncerRMIBroadcast");
		}
		Master.playerHasDisconnected((MessagingChannelInterface) channel);
	}
	
	@Override
	public void unregisterClient(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI del = findChannel(client);
		if (del != null) {
			unregisterChannel(del);
		}
		LOGGER.info("Client " + client.toString() + " unregistered");
	}

	@Override
	public void rename(String message, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			Master.getGameMasterOfPlayer(player).renamePlayer(player, message);
		} else {
			LOGGER.warning("MessagingChannelInterface is missing");
			throw new RemoteException("You should have registered first");
		}
			
		LOGGER.info("Client " + client.toString() + " renamed himself");
	}

	@Override
	public void globalChat(String message, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			Master.getGameMasterOfPlayer(player).globalChat(player, message);
		} else {
			LOGGER.warning("MessagingChannelInterface is missing");
			throw new RemoteException("You should have registered first");
		}
		LOGGER.info("Client " + client.toString() + " sent a chat message");
	}

	@Override
	public void whoAmI(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			((MessagingChannelRMI) pla).getClient().renameMyself(player.getName());
		} else {
			LOGGER.warning("MessagingChannelInterface is missing");
			throw new RemoteException("You should have registered first");
		}
		LOGGER.info("Client " + client.toString() + " sent whoami");
	}

	@Override
	public void whereAmI(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			String myPos = Master.getGameMasterOfPlayer(player).getPlayerPosition(player);
			((MessagingChannelRMI) pla).getClient().setMyPosition(myPos);
		} else {
			LOGGER.warning("MessagingChannelInterface is missing");
			throw new RemoteException("You should have registered first");
		}
		LOGGER.info("Client " + client.toString() + " sent whereami");
	}

	@Override
	public void setAnswer(String answer, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI chan = findChannel(client);
		if (chan != null) {
			chan.setAnswer(answer);
		} else {
			LOGGER.warning("MessagingChannelInterface is missing");
			throw new RemoteException("You should have registered first");
		}
		LOGGER.info("Client " + client.toString() + " sent answer: " + answer);
	}
	
	@Override
	public void pong(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI chan = findChannel(client);
		if (chan != null) {
			pinger.getPong(client);
		} else {
			LOGGER.warning("MessagingChannelInterface is missing");
			throw new RemoteException("You should have registered first");
		}
	}
	
	/**This method sets up the Registry and creates and exposes the ServerRMICore Remote Object;
	 *  after the ServerRMICore class invoked this, the clients using RMI will be able to invoke functions.
	 * @throws RemoteException 
	 * @throws MalformedURLException */
	public static void initializer(GlobalSettings locals) throws RemoteException, MalformedURLException {
		LogHelper.setDefaultOptions(LOGGER);
		SingleShutdownHook.setHook(new Thread(new ServerShutdownHook()));
		LOGGER.info("Creating local registry");
		LocateRegistry.createRegistry(REGISRTY_PORT);
		LOGGER.info("Created RMI registry on port " + REGISRTY_PORT);
		ServerRemoteInterface server = getServerInstance(locals);
		UnicastRemoteObject.exportObject(server, locals.getServerPort());
		LOGGER.info("Exported server interface");
		Naming.rebind("//localhost/ServerSocketCore", server);
		LOGGER.info("ServerSocketCore interface bound to name: \"" + "//localhost/ServerSocketCore" + "\"");
	}
	
	
	private void tryStartPinging() {
		if (clientsList.size() > 0 && pinger == null) {
			pinger = new PingSender(clientsList, this);
			new Thread(pinger).start();
			LOGGER.info("Started pinger thread");
		} else {
			pinger.restart();
		}
	}
	
	private void tryStopPinging() {
		if (clientsList.size() <= 0) {
			pinger.stopPinging();
			LOGGER.info("Stopped pinger thread");
			pinger = null;
		}
	}
}
