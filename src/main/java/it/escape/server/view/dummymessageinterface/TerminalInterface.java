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
	public void tailRead() {
		while (!transmitQueue.isEmpty()) {
			String next = transmitQueue.poll();
			System.out.println(next);
		}
	}
	
	@Override
	public void tailWrite() {
		String in = cliInput.next();
		reciveQueue.offer(in);
		afterTailWrite();
	}
}
