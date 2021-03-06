package it.escape.core.server.view;

import java.rmi.RemoteException;
import java.util.Observer;
import java.util.logging.Logger;

import it.escape.core.client.connection.rmi.ClientRemoteInterface;
import it.escape.core.server.view.rmispecific.ServerRemoteInterface;
import it.escape.tools.utils.LogHelper;

/**
 * The class must be used this way:
 * The initialization is performed by the serverRMI
 * (1) The utilizer class will get the client reference,
 * and call its remote methods to ask questions,
 * (2) The the utilizer will call getAnswer(), which will
 * block the execution flow until the server has set the answer.
 * 
 * The override default mechanism works as usual.
 * @author michele, andrea
 *
 */
public class MessagingChannelRMI implements MessagingChannelInterface {
	
	private static final Logger LOGGER = Logger.getLogger( MessagingChannelRMI.class.getName() );
	
	private ClientRemoteInterface client;
	
	private ServerRemoteInterface server;
	
	private String defaultOption;
	
	private String answer;
	
	private static int idGenerator = 0;
	
	private int clientIdentifier;
	
	private boolean answerArrived;
	
	/**The constructor; sets up clientRemote for communication, serverRemote to unregister clients,
	 * a unique numeric ID, and the logger.*/
	public MessagingChannelRMI(ClientRemoteInterface client, ServerRemoteInterface server) {
		LogHelper.setDefaultOptions(LOGGER);
		this.client = client;
		this.server = server;
		this.clientIdentifier = idGenerator;
		idGenerator++;
		answerArrived = false;
	}

	public ClientRemoteInterface getClient() {
		return client;
	}
	
	public int getID() {
		return clientIdentifier;
	}
	
	/**This method is invoked by the Server, and it waits for an answer (String) to be provided.
	 * The answer will be provided either by setAnswer(String answer) (invoked by the Client),
	 * or by overrideDefault() (invoked by TimeController's thread)*/
	public synchronized String getAnswer() {
		try {
			do {
				wait();
			}while (!answerArrived);
		} catch (InterruptedException e) {
		}
		answerArrived = false;	//sets up the variable to false, so we'll be ready to listen again
		return answer;
	}
	
	public void setDefaultOption(String option) {
		defaultOption = option;
	}

	/**This method is invoked by the method setAnswer(String answer, ClientRemoteInterface client) in ServerRMICore,
	 * which is invoked by the client. */
	public synchronized void setAnswer(String answer) {
		this.answer = answer;
		answerArrived = true;
		notify();
	}
	
	public synchronized void overrideDefault() {
		answer = defaultOption;
		answerArrived = true;
		LOGGER.fine("Overriding with default: \"" + defaultOption + "\"");
		notify();
	}

	/** here for compatibility across MessagingChannel(s), it doesn't
	 * actually do anything. */
	public void addObserver(Observer o) {
	}

	public void killConnection() {
		try {
			server.unregisterClient(client);
		} catch (RemoteException e) {
			LOGGER.warning("Cannot kill the connection: " + e.getMessage());
		}
	}
}
