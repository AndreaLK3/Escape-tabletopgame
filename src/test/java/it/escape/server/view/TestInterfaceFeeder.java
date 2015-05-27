package it.escape.server.view;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestInterfaceFeeder implements Runnable {

	private String gold;
	
	private String garbage;
	
	private AtomicBoolean running;
	
	private TestingInterface iface;
	
	private static final int SLEEP = 50;
	
	public TestInterfaceFeeder(String gold, String garbage, TestingInterface iface) {
		running = new AtomicBoolean();
		running.set(true);
		this.iface = iface;
		this.gold = gold;
		this.garbage = garbage;
	}

	public void run() {
		while (running.get()) {
			iface.receiveFromClient(garbage);
			iface.receiveFromClient(gold);
			iface.receiveFromClient(garbage);
			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void stop() {
		running.set(false);
	}

}
