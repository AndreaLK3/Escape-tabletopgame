package it.escape.client;

import it.escape.client.controller.cli.StateManagerCLIInterface;
import it.escape.client.controller.gui.ClientProceduresInterface;

import java.rmi.Remote;

public interface ClientRemoteInterface extends Remote, ClientProceduresInterface {

	public abstract void showMessageInTerminal (String message);
	
}
