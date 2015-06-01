package it.escape.server.view.dummymessageinterface;

import it.escape.server.view.MessagingChannel;

public class DummyTimer implements Runnable {
	
	private MessagingChannel iface;

	public DummyTimer(MessagingChannel iface) {
		this.iface = iface;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000*20);
			} catch (InterruptedException e) {
			}
			System.out.println("timer: -- timeout hit!");
			iface.overrideDefaultOption();
		}
	}

}
