package it.escape.server.view;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketInterface extends MessagingInterface {
	
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
	public void receiveFromClient() {
		String read = in.nextLine().toLowerCase();
		clientToServerQueue.offer(read);
		afterTailWrite();
	}

}