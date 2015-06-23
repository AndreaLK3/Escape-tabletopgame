package it.escape.core.client;

import it.escape.core.client.connection.socket.ClientSocketChannel;
import it.escape.core.client.controller.ClientChannelInterface;
import it.escape.core.client.controller.Relay;
import it.escape.core.client.controller.RelayForSocket;
import it.escape.core.client.controller.gui.UpdaterSwing;
import it.escape.core.client.model.ModelForGUI;
import it.escape.core.client.view.gui.SmartSwingView;
import it.escape.tools.GlobalSettings;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.locks.ReentrantLock;

public class ClientInitializerGUISocket extends ClientInitializerGUI {
	
	private static ClientSocketChannel connection;
	
	private static Thread connectionThread;
	
	private static Relay relay;
	
	private static UpdaterSwing updater;
	
	private static ModelForGUI model;
	
	public static void start(GlobalSettings localSettings) {
		ReentrantLock finalPhase = new ReentrantLock();
		locals = localSettings;
		openProgressDialog();
		
		try {
			// connect to server
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			
			pleaseWait.dispose();  // remove the "connecting..." dialog
			
			// initialize other stuff, like the controller
			bindReferences();
			
			// start the view
			SmartSwingView.synchronousLaunch((BindUpdaterInterface)updater, relay, model, finalPhase, connection);
			
			// start reading from the network *only* once the gui is up
			connectionThread = new Thread(connection);
			connectionThread.start();
			
			finalPhase.lock();
			// join connection thread, when the connection goes down, the program will stop
			try {
				connectionThread.join();
			} catch (InterruptedException e) {
				popupError("(Minor: unexpected interruption during shutdown)");
			}
			
		} catch (UnknownHostException e) {
			pleaseWait.dispose();
			popupError("Cannot resolve the hostname");
		} catch (IOException e) {
			pleaseWait.dispose();
			popupError("Cannot connect to the server");
		}
	}
	
	
	private static void bindReferences() {
		relay = new RelayForSocket((ClientChannelInterface) connection);
		model = new ModelForGUI();
		updater = new UpdaterSwing(model);
		connection.addObserver(updater);
	}
	
	
}
