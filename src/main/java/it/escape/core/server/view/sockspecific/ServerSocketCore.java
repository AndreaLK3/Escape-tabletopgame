package it.escape.core.server.view.sockspecific;

import it.escape.core.server.ServerShutdownHook;
import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.LogHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**This class handles the creation of the TCP connections when the clients connect.
 * (n: We could make it a Singleton...)
 */
public class ServerSocketCore implements ServerInterface{
	
	private static final Logger log = Logger.getLogger( ServerSocketCore.class.getName() );
	
	private GlobalSettings locals;
	
	private static ServerSocketCore serverInstance = null;
	private static List<Connection> connections = new ArrayList<Connection>();
	private final int PORT;
	private ServerSocket serverSocket;
	
	public static ServerSocketCore createServerInstance(GlobalSettings locals) throws IOException {
		if (serverInstance == null) {
			serverInstance = new ServerSocketCore(locals);
		}
		return serverInstance;
	}
	
	public static ServerSocketCore getServerInstance() {
		return serverInstance;
	}

	/**The constructor: it tries to initialize the ServerSocket at the given port.
	 * @throws IOException
	 */
	private ServerSocketCore(GlobalSettings locals) throws IOException {
		LogHelper.setDefaultOptions(log);
		this.locals = locals;
		PORT = this.locals.getServerPort();
		Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutdownHook()));
		this.serverSocket = new ServerSocket(PORT);
		log.info("ServerSocketCore is now listening on port " + PORT);
	}
	
	/**This is the main server loop: it listens to the incoming Socket connections, 
	 * when a new user connects it handles the initialization to a new thread */
	public void run(){
		while(true){
			try {
				Socket newSocket = serverSocket.accept();
				Connection c = new Connection(newSocket, this);
				registerConnection(c);
				log.info("A new user connected from " + newSocket.getInetAddress().toString());
				new Thread(c).start();
			} catch (IOException e) {
				log.severe("Connection error!");
			}
		}
	}
	
	public GlobalSettings getLocals() {
		return locals;
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
