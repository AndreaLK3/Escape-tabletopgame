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
		iface.setDefaultOption("default option thing");
		iface.headWrite("Scrivi qualcosa");
		System.out.println("now we wait");
		String ans = iface.headRead();
		System.out.println("Controller: ho ricevuto " + ans);
	}

}
