package it.escape.client;

import java.io.PrintStream;
import java.util.Scanner;

import it.escape.client.controller.Relay;
import it.escape.client.controller.StateManager;
import it.escape.client.controller.UpdaterCLItoTerminalInterface;
import it.escape.client.view.cli.Terminal;

public class CLIAdapter extends Terminal implements UpdaterCLItoTerminalInterface {

	public CLIAdapter(Relay relay, StateManager stateManager, Scanner in,
			PrintStream out) {
		super(relay, stateManager, in, out);
	}
	
}
