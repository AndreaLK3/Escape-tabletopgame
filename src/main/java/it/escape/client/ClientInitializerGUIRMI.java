package it.escape.client;

import it.escape.client.connection.rmi.ClientRemoteInitializer;
import it.escape.client.connection.rmi.ProxyToServer;
import it.escape.client.controller.ClientChannelInterface;
import it.escape.client.controller.Relay;
import it.escape.client.controller.RelayForRMI;
import it.escape.client.controller.gui.UpdaterSwing;
import it.escape.client.model.ModelForGUI;
import it.escape.client.view.gui.SmartSwingView;
import it.escape.utils.FilesHelper;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ClientInitializerGUIRMI extends ClientInitializerGUI{
	
	private static ProxyToServer remoteServer;
	
	private static Relay relay;
	
	private static UpdaterSwing updater;
	
	private static ModelForGUI model;
	
	public static void start(ClientLocalSettings Locals) {
		ReentrantLock finalPhase = new ReentrantLock();
		locals = Locals;
		openProgressDialog();
		
		model = new ModelForGUI();
		updater = new UpdaterSwing(model);
		
		// connect to server
		ClientRemoteInitializer.setSwingMode(updater);
		remoteServer = ClientRemoteInitializer.initializer(locals);
		relay = new RelayForRMI((ClientChannelInterface) remoteServer);
		
		// start the view
		SmartSwingView.synchronousLaunch((BindUpdaterInterface)updater, relay, model, finalPhase, remoteServer);		
		
		// register to server and start the actual communication
		ClientRemoteInitializer.postponedStart();
		
		pleaseWait.dispose();  // remove the "connecting..." dialog	
		
		finalPhase.lock();
		
	}
	
	
}
