package it.escape.server.view;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**This class handles the creation of the TCP connections when the clients connect.
 * (n: We could make it a Singleton...)
 */
public class Server {
	
	private static final int PORT = 9050;
	private ServerSocket serverSocket;

	/**The constructor: it tries to initialize the ServerSocket at the given port.
	 * @throws IOException
	 */
	public Server() throws IOException {
		this.serverSocket = new ServerSocket(PORT);
	}
	
	public void run(){
		while(true){
			try {
				Socket newSocket = serverSocket.accept();
				//Connection c = new Connection(newSocket, this);
				//registerConnection(c);
				//new Thread(c).start();
			} catch (IOException e) {
				System.out.println("Errore di connessione!");
			}
		}
	}
	
	/*private synchronized void registerConnection(Connection c){
		connections.add(c);*/
	}

