package it.escape.server.view.dummymessageinterface;

import it.escape.server.view.MessagingInterface;

import java.util.Scanner;

public class DummyMain {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		MessagingInterface iface = new TerminalInterface(in);
		DummyBackend controller = new DummyBackend(iface);
		
		(new Thread(controller)).start();
		
		while (true) {
			iface.tailWrite();
		}
	}

}
