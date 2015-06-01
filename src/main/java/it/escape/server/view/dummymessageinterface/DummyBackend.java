package it.escape.server.view.dummymessageinterface;

import java.util.Arrays;

import it.escape.server.view.MessagingChannel;

public class DummyBackend implements Runnable{

	private MessagingChannel iface;
	
	public DummyBackend(MessagingChannel iface) {
		this.iface = iface;
	}

	public void run() {
		iface.setDefaultOption("rei ayanami");  // :P the default option is not bound by the context
		iface.setContext(Arrays.asList("eren","mikasa","armin","levi"));
		while (true) {
			testSquence();
		}
		
	}
	
	private void testSquence() {
		System.out.println("controller: -- doing stuff");
		iface.writeToClient("Scrivi qualcosa");
		System.out.println("controller: -- now we wait");
		String ans = iface.readFromClient();
		System.out.println("Controller: ho ricevuto \"" + ans + "\"");
	}

}
