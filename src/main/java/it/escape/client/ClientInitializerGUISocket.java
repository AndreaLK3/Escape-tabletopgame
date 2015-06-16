package it.escape.client;

import it.escape.client.connection.socket.ClientSocketChannel;
import it.escape.client.controller.ClientStringChannelInterface;
import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.UpdaterSwing;
import it.escape.client.model.ModelForGUI;
import it.escape.client.view.gui.SmartSwingView;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ClientInitializerGUISocket {
	
	private static ClientSocketChannel connection;
	
	private static ClientLocalSettings locals;
	
	private static Thread connectionThread;
	
	private static JDialog pleaseWait;
	
	private static Relay relay;
	
	private static UpdaterSwing updater;
	
	private static ModelForGUI model;
	
	public static void start(ClientLocalSettings Locals) {
		ReentrantLock finalPhase = new ReentrantLock();
		locals = Locals;
		openProgressDialog();
		
		try {
			// connect to server
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			
			pleaseWait.dispose();  // remove the "connecting..." dialog
			// initialize other stuff, like the controller
			relay = new Relay((ClientStringChannelInterface) connection);
			model = new ModelForGUI();
			updater = new UpdaterSwing(model);
			connection.addObserver(updater);
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
			}
			
		} catch (UnknownHostException e) {
			pleaseWait.dispose();
			popupError("Cannot resolve the hostname");
		} catch (IOException e) {
			pleaseWait.dispose();
			popupError("Cannot connect to the server");
		}
	}
	
	private static void openProgressDialog() {
		JOptionPane optionPane;
		try {
			optionPane = new JOptionPane(
					"Connecting to " + locals.getDestinationServerAddress() + ":" + locals.getServerPort(),
					JOptionPane.INFORMATION_MESSAGE, 
					JOptionPane.DEFAULT_OPTION,
					new ImageIcon(FilesHelper.getResourceAsByteArray("resources/artwork/launcher/connecting.gif")),
					new Object[]{},
					null);
			pleaseWait = new JDialog();
			pleaseWait.setTitle("Connecting...");
			pleaseWait.setLocationRelativeTo(null);
			pleaseWait.setContentPane(optionPane);
			pleaseWait.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			pleaseWait.pack();
			pleaseWait.setVisible(true);
		} catch (IOException e) {
			popupError("Cannot initialize dialog");
		}
	}
	
	private static void popupError(String message) {
		JOptionPane.showMessageDialog(null,
			    message,
			    "Network error",
			    JOptionPane.WARNING_MESSAGE);
	}
}