package it.escape.core.client;

import java.io.PrintStream;
import java.util.Scanner;

import it.escape.tools.GlobalSettings;
import it.escape.tools.strings.StringRes;

public class ClientInitializerCLI {
	
	protected static GlobalSettings locals;
	
	protected static Scanner in = new Scanner(System.in);
	
	protected static PrintStream out = System.out;
	
	/**The constructor (it doesn't really do anything, since we use only the static members of this class*/
	public ClientInitializerCLI() {}
	
	protected static void enterServerAddress() {
		out.println(String.format(
				StringRes.getString("client.text.enterServerAddress"),
				locals.getDestinationServerAddress()));
		String input = in.nextLine();
		if (!"".equals(input)) {
			locals.setDestinationServerAddress(input);
		}
	}
}
