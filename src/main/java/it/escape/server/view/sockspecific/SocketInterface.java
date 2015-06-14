package it.escape.server.view.sockspecific;

import it.escape.server.view.MessagingChannelStrings;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A MessagingChannelStrings which make use of a socket.
 * The socket is opened / closed[1] elsewhere, we only
 * make use of it.
 * 
 *   [1] but we can trigger its termination
 * @author michele
 *
 */
public class SocketInterface extends MessagingChannelStrings {
	
	private Socket clientSocket;
	
	private Scanner in;
	
	private PrintStream out;

	public SocketInterface(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		in = new Scanner(clientSocket.getInputStream());
		out = new PrintStream(clientSocket.getOutputStream());
	}
	
	@Override
	public void sendToClient(String singleMessage) {
		out.println(singleMessage);
	}
	
	@Override
	public void receiveFromClient() throws NoSuchElementException {
		String read = in.nextLine();
		enqueueFacility(read);
		afterTailWrite();
	}
	
	/**
	 * Once we have closed the output stream, the Socket will
	 * stop working.
	 * The Connection thread will sense it, and do all the
	 * required cleanup.
	 */
	@Override
	public void killConnection() {
		out.close();
		setConnectionDead();  // a bit useless, since we don't plan to use this channel anymore
	}

	
}
