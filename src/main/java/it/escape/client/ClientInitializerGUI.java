package it.escape.client;

import it.escape.client.controller.ClientSocketChannelInterface;
import it.escape.client.controller.Relay;
import it.escape.client.controller.gui.UpdaterSwing;
import it.escape.client.graphics.BindUpdaterInterface;
import it.escape.client.graphics.Displayer;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ClientInitializerGUI {
	
	private static ClientSocketChannel connection;
	
	private static ClientLocalSettings locals;
	
	private static Thread connectionThread;
	
	private static JDialog pleaseWait;
	
	private static Relay relay;
	
	private static UpdaterSwing updater;
	
	public static void start(ClientLocalSettings Locals) {
		locals = Locals;
		openProgressDialog();
		
		try {
			// connect to server
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			
			pleaseWait.dispose();  // remove the "connecting..." dialog
			// initialize other stuff, like the controller
			relay = new Relay((ClientSocketChannelInterface) connection);
			updater = new UpdaterSwing();
			connection.addObserver(updater);
			// start the view
			Displayer.synchronousLaunch((BindUpdaterInterface)updater, relay);
			
			// start reading from the network
			connectionThread = new Thread(connection);
			connectionThread.start();
			
			// join connection thread
			try {
				connectionThread.join();
			} catch (InterruptedException e) {
			}
			
		} catch (UnknownHostException e) {
			pleaseWait.dispose();
			popupError("Unknown host");
		} catch (IOException e) {
			pleaseWait.dispose();
			popupError("Socket write error");
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
