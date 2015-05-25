package it.escape.server.view.dummymessageinterface;

import it.escape.server.view.MessagingInterface;

public class DummyTimer implements Runnable {
	
	private MessagingInterface iface;

	public DummyTimer(MessagingInterface iface) {
		this.iface = iface;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000*20);
			} catch (InterruptedException e) {
			}
			System.out.println("timeout hit!");
			iface.overrideDefaultOption();
		}
	}

}
