package it.escape.server.view.dummymessageinterface;

import java.util.Scanner;

import it.escape.server.view.MessagingChannel;

public class TerminalInterface extends MessagingChannel {
	
	private Scanner cliInput;
	
	
	
	public TerminalInterface(Scanner cliInput) {
		super();
		this.cliInput = cliInput;
	}

	@Override
	public void sendToClient(String singleMessage) {
		System.out.println(singleMessage);
	}
	
	@Override
	public void receiveFromClient() {
		String in = cliInput.next().toLowerCase();
		enqueueFacility(in);
		afterTailWrite();
	}
}
