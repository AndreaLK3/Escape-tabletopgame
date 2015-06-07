package it.escape.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ClientInitializerGUI {
	
	private static ClientSocketChannel connection;
	
	private static ClientLocalSettings locals;
	
	private static Thread connectionThread;
	
	public static void start(ClientLocalSettings Locals) {
		locals = Locals;
		try {
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			
			// initialize other stuff
			
			connectionThread = new Thread(connection);
			
			connectionThread.start();
			// start the view
			
			// join connection & view threads
			
		} catch (UnknownHostException e) {
			popupError("Unknown host");
		} catch (IOException e) {
			popupError("Socket write error");
		}
	}
	
	private static void popupError(String message) {
		JOptionPane.showMessageDialog(null,
			    message,
			    "Network error",
			    JOptionPane.WARNING_MESSAGE);
	}
}
