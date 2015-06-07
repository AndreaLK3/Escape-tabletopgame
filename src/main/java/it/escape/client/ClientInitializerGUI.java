package it.escape.client;

import it.escape.client.Graphics.Displayer;
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
	
	public static void start(ClientLocalSettings Locals) {
		locals = Locals;
		openProgressDialog();
		
		try {
			connection = new ClientSocketChannel(
					locals.getDestinationServerAddress(),
					locals.getServerPort());
			
			pleaseWait.dispose();
			// initialize other stuff
			
			connectionThread = new Thread(connection);
			
			connectionThread.start();
			// start the view
			String param[] = {""};
			Displayer.main(param);
			
			// join connection & view threads
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
		new Thread(new Runnable() {
			@Override
			public void run() {
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
		}).start();
		
	}
	
	private static void popupError(String message) {
		JOptionPane.showMessageDialog(null,
			    message,
			    "Network error",
			    JOptionPane.WARNING_MESSAGE);
	}
}
