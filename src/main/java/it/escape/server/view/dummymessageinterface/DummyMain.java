package it.escape.server.view.dummymessageinterface;

import it.escape.server.view.MessagingInterface;

import java.util.Scanner;

public class DummyMain {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		MessagingInterface iface = new TerminalInterface(in);
		DummyBackend controller = new DummyBackend(iface);
		DummyTimer timer = new DummyTimer(iface);
		
		(new Thread(controller)).start();
		(new Thread(timer)).start();
		
		while (true) {
			iface.tailWrite();
		}
	}

}
