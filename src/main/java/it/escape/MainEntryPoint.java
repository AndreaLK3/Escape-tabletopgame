package it.escape;

import it.escape.client.ClientInitializerCLI;
import it.escape.client.Graphics.Displayer;
import it.escape.launcher.StartMenu;
import it.escape.launcher.menucontroller.StartMenuInterface;
import it.escape.launcher.menucontroller.StartSubsystemsInterface;
import it.escape.server.ServerInitializer;
import it.escape.strings.StringRes;

import java.awt.HeadlessException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

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
		
		new MainEntryPoint();
		
	}
	
	private MainEntryPoint() {
		if (globals.isStartInTextClient()) {
			ClientInitializerCLI.start(globals);
		} else if (globals.isStartInTextServer()) {
			new ServerInitializer(globals);
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

	@Override
	public void startTextSocketClient(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					ClientInitializerCLI.start(globals);
					startMenu.closeProgram();
				}}).start();
	}

	@Override
	public void startTextSocketServer(final StartMenuInterface startMenu) {
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					new ServerInitializer(globals);
					startMenu.closeProgram();
				}}).start();
	}

	@Override
	public void startGUISocketClient() {
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					String param[] = {""};
					Displayer.main(param);
				}}).start();
	}

}
