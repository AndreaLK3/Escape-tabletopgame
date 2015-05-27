package it.escape.server.view;

import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**This class handles the creation of the TCP connections when the clients connect.
 * (n: We could make it a Singleton...)
 */
public class Server implements ConnectionUnregisterInterface{
	
	protected static final Logger log = Logger.getLogger( Server.class.getName() );
	
	private static Server serverInstance = null;
	private static List<Connection> connections = new ArrayList<Connection>();
	private static final int PORT = 1337;
	private ServerSocket serverSocket;
	
	public static Server createServerInstance() throws IOException {
		if (serverInstance == null) {
			serverInstance = new Server();
		}
		return serverInstance;
	}
	
	public static Server getServerInstance() {
		return serverInstance;
	}

	/**The constructor: it tries to initialize the ServerSocket at the given port.
	 * @throws IOException
	 */
	private Server() throws IOException {
		LogHelper.setDefaultOptions(log);
		this.serverSocket = new ServerSocket(PORT);
		log.info("Server is now listening on port " + PORT);
	}
	
	public void run(){
		while(true){
			try {
				Socket newSocket = serverSocket.accept();
				Connection c = new Connection(newSocket, this);
				registerConnection(c);
				log.info("A new user connected from " + newSocket.getInetAddress().toString());
				registerConnection(c);
				new Thread(c).start();
			} catch (IOException e) {
				log.severe("Connection error!");
			}
		}
	}
	
	private synchronized void registerConnection(Connection c){
		connections.add(c);
		log.finer(StringRes.getString("view.server.connectionRegistered"));
	}

	public void unregisterConnection(Connection connection) {
		connections.remove(connection);
		log.finer(StringRes.getString("view.server.connectionUnregistered"));
	}
}

