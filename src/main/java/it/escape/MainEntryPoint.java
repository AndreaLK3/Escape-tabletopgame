package it.escape;

import it.escape.client.ClientInitializerCLI;
import it.escape.launcher.StartMenu;
import it.escape.server.ServerInitializer;
import it.escape.strings.StringRes;

import java.awt.HeadlessException;
import java.io.PrintStream;

public class MainEntryPoint {
	
	private static PrintStream out = System.out;

	public static void main(String[] args) {
		
		new CliParser(args).parse();
		
		if (GlobalSettings.isStartInTextClient()) {
			ClientInitializerCLI.start();
		} else if (GlobalSettings.isStartInTextServer()) {
			new ServerInitializer();
		} else {
			try {
				StartMenu.launch();
			} catch (HeadlessException e) {
				out.println(String.format(
						StringRes.getString("launcher.warn.headless"),
						StringRes.getString("cliparser.option.long.textclient"),
						StringRes.getString("cliparser.option.long.textserver")));
			}
		}
		
	}

}
