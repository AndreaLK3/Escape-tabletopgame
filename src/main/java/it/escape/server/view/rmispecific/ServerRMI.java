package it.escape.server.view.rmispecific;

import it.escape.client.connection.rmi.ClientRemoteInterface;
import it.escape.server.Master;
import it.escape.server.ServerLocalSettings;
import it.escape.server.controller.UserMessagesReporter;
import it.escape.server.controller.UserMessagesReporterSocket;
import it.escape.server.model.AnnouncerRMIBroadcast;
import it.escape.server.model.SuperAnnouncer;
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
	
	protected static final Logger LOG = Logger.getLogger( ServerRMI.class.getName() );

	// it's more useful to list the MessagingChannelRMI, which match 1:1 to the active clients
	private List<MessagingChannelRMI> clientsList;
	
	private ServerLocalSettings locals;
	
	private MessagingChannelRMI findChannel(ClientRemoteInterface client) {
		for (MessagingChannelRMI c : clientsList) {
			if (c.getClient() == client) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Basically does the same things that Connection used to
	 * do upon establishing
	 */
	@Override
	public void registerClient(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface channel = new MessagingChannelRMI(client, this);
		clientsList.add((MessagingChannelRMI) channel);
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
			LOG.warning(StringRes.getString("view.connection.cantWelcome"));
		}
		
	}

	@Override
	public void unregisterClient(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI del = findChannel(client);
		if (del != null) {
			clientsList.remove(del);
			((AnnouncerRMIBroadcast) UserMessagesReporter.getReporterInstance(del).getAnnouncer()).unSubscribe(client);
		}
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
		}
	}

	@Override
	public void globalChat(String message, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			Master.getGameMasterOfPlayer(player).globalChat(player, message);
		}
	}

	@Override
	public void whoAmI(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			((MessagingChannelRMI) pla).getClient().renameMyself(player.getName());
		}
	}

	@Override
	public void whereAmI(ClientRemoteInterface client) throws RemoteException {
		MessagingChannelInterface pla = (MessagingChannelInterface) findChannel(client);
		if (pla != null) {
			Player player = UserMessagesReporter.getReporterInstance(pla).getThePlayer();
			String myPos = Master.getGameMasterOfPlayer(player).getPlayerPosition(player);
			((MessagingChannelRMI) pla).getClient().setMyPosition(myPos);
		}
	}

	@Override
	public void setAnswer(String answer, ClientRemoteInterface client) throws RemoteException {
		MessagingChannelRMI ans = findChannel(client);
		if (ans != null) {
			ans.setAnswer(answer);
		}
	}
	
	/**This method sets up the Registry and creates and exposes the Server Remote Object;
	 *  after the Server invoked this, the clients using RMI will be able to invoke functions.
	 * @throws RemoteException 
	 * @throws MalformedURLException */
	public static void initializer(ServerLocalSettings locals) throws RemoteException, MalformedURLException {
		LogHelper.setDefaultOptions(LOG);
		LOG.info("Creating local registry");
		LocateRegistry.createRegistry(1099);
		LOG.info("Created RMI registry on port 1099");
		ServerRemoteInterface server = new ServerRMI(locals);
		UnicastRemoteObject.exportObject(server, 0);
		LOG.info("Exported server interface");
		Naming.rebind("//localhost/Server", server);
		LOG.info("Server interface bound to name: \"" + "//localhost/Server" + "\"");
	}
	
	/**Constructor for the object*/
	public ServerRMI(ServerLocalSettings locals) {
		this.locals = locals;
		clientsList = new ArrayList<MessagingChannelRMI>();
	}

}
