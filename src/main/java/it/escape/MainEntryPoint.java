package it.escape;

import it.escape.client.ClientInitializerCLISocket;
import it.escape.client.ClientInitializerGUIRMI;
import it.escape.client.ClientInitializerGUISocket;
import it.escape.launcher.StartMenu;
import it.escape.launcher.menucontroller.StartMenuInterface;
import it.escape.launcher.menucontroller.StartSubsystemsInterface;
import it.escape.server.RMIServerInitializer;
import it.escape.server.SockServerInitializer;
import it.escape.strings.StringRes;

import java.awt.HeadlessException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main entry point of the whole application.
 * Its sequence of operation is the following:
 * 1) Parse the command line parameters
 * 2) Attempt to set the Swing look and Feel to something that
 *    is not completely hideous
 * 3) Attempt to start the graphical launcher. If that's not
 *    possible, write an error message and return
 *    
 * The class also offers an interface to start the actual game.
 * Said interface is intended to be used by the launcher
 * @author michele
 *
 */
public class MainEntryPoint implements StartSubsystemsInterface {
	
	private static PrintStream out = System.out;
	
	private static GlobalSettings globals = new GlobalSettings();
	
	private static String[] lafPreference = {
			"javax.swing.plaf.nimbus.NimbusLookAndFeel",  // very nice
			"com.sun.java.swing.plaf.gtk.GTKLookAndFeel",  // ok
			"com.sun.java.swing.plaf.windows.WindowsLookAndFeel",  // still better than metal
			"javax.swing.plaf.metal.MetalLookAndFeel",  // metal. ugh.
			"com.sun.java.swing.plaf.motif.MotifLookAndFeel"  // barf
	};

	public static void main(String[] args) {
		
		new CliParser(args).parse(globals);
		
		MainEntryPoint ourMain = new MainEntryPoint();
		ourMain.initialize();
		
	}
	
	
	private void initialize() {
		// this is just for initialization via command line
		// if you want to start rmi here, add a switch in the command line
		if (globals.isStartInTextClient()) {
			ClientInitializerCLISocket.start(globals);
		} else if (globals.isStartInTextServer()) {
			new SockServerInitializer().startSocketServer(globals);
			//new RMIServerInitializer().startRMIServer(globals);
		} else {
			try {
				lookAndFeel();
				StartMenu.launch(globals, this);
			} catch (HeadlessException e) {
				out.println(String.format(
						StringRes.getString("launcher.warn.headless"),
						StringRes.getString("cliparser.option.long.textclient"),
						StringRes.getString("cliparser.option.long.textserver")));
			}
		}
	}

	private MainEntryPoint() {
		
	}
	
	private static void lookAndFeel() {
		try {
			List<String> looks = new ArrayList<String>();
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				looks.add(info.getClassName());
			}
			for (String pref : Arrays.asList(lafPreference)) {
				if (looks.contains(pref)) {
					UIManager.setLookAndFeel(pref);
					break;
				}
			}
			
		} catch (ClassNotFoundException e) {
			out.println("Error setting look and feel.");
		} catch (InstantiationException e) {
			out.println("Error setting look and feel.");
		} catch (IllegalAccessException e) {
			out.println("Error setting look and feel.");
		} catch (UnsupportedLookAndFeelException e) {
			out.println("Error setting look and feel.");
		}
	}
	
	private static void graphicalEnterIp() {
		String newAddr = null;
		while (newAddr == null || newAddr.length() == 0) {
			newAddr = (String) JOptionPane.showInputDialog(null,
					"Please enter the server's hostname or IP:",
					"Connect to...",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					globals.getDestinationServerAddress());
		}
		globals.setDestinationServerAddress(newAddr);
	}
	
	public void startTextRMIServer(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				public void run() {
					new RMIServerInitializer().startRMIServer(globals);
					//startMenu.closeProgram();
				}}).start();
	}
	
	@Override
	public void startTextSocketServer(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				public void run() {
					new SockServerInitializer().startSocketServer(globals);
					startMenu.closeProgram();
				}}).start();
	}
	
	
	public void startTextComboServer(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				public void run() {
					// MUST be run in this exact order
					new RMIServerInitializer().startRMIServer(globals);
					new SockServerInitializer().startSocketServer(globals);
					startMenu.closeProgram();
				}}).start();
	}
	
	public void startTextSocketClient(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				public void run() {
					ClientInitializerCLISocket.start(globals);
					startMenu.closeProgram();
				}}).start();
	}

	public void startGUISocketClient(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				public void run() {
					graphicalEnterIp();
					ClientInitializerGUISocket.start(globals);
					startMenu.closeProgram();
				}}).start();
	}
	
	public void startGUIRMIClient(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				public void run() {
					graphicalEnterIp();
					ClientInitializerGUIRMI.start(globals);
					startMenu.closeProgram();
				}}).start();
	}

}
