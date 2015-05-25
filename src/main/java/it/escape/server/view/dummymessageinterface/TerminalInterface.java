package it.escape.server.view.dummymessageinterface;

import java.util.Scanner;

import it.escape.server.view.MessagingInterface;

public class TerminalInterface extends MessagingInterface {
	
	private Scanner cliInput;
	
	
	
	public TerminalInterface(Scanner cliInput) {
		super();
		this.cliInput = cliInput;
	}

	@Override
	public void tailRead(String singleMessage) {
		System.out.println(singleMessage);
	}
	
	@Override
	public void tailWrite() {
		String in = cliInput.next().toLowerCase();
		reciveQueue.offer(in);
		afterTailWrite();
	}
}
