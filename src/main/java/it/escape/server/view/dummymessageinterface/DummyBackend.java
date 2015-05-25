package it.escape.server.view.dummymessageinterface;

import it.escape.server.view.MessagingInterface;

public class DummyBackend implements Runnable{

	private MessagingInterface iface;
	
	public DummyBackend(MessagingInterface iface) {
		this.iface = iface;
	}

	public void run() {
		while (true) {
			testSquence();
		}
		
	}
	
	private void testSquence() {
		System.out.println("controller: -- doing stuff");
		iface.setDefaultOption("default option thing");
		iface.headWrite("Scrivi qualcosa");
		System.out.println("controller: -- now we wait");
		String ans = iface.headRead();
		System.out.println("Controller: ho ricevuto \"" + ans + "\"");
	}

}
