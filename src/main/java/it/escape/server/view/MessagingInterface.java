package it.escape.server.view;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sun.jndi.cosnaming.CNNameParser;

import it.escape.server.controller.MessagingHead;

/**
 * 
 * @author michele, andrea
 */
public class MessagingInterface implements MessagingHead, MessagingTail {
	
	protected Queue<String> serverToClientQueue;
	protected Queue<String> clientToServerQueue;
	
	private List<String> context = null;
	
	private String defaultOption;
	
	private AtomicBoolean connetctionAlive;
	
	private AtomicBoolean override;
	
	public MessagingInterface() {
		serverToClientQueue = new ConcurrentLinkedQueue<String>();
		clientToServerQueue = new ConcurrentLinkedQueue<String>();
		override = new AtomicBoolean();
		connetctionAlive = new AtomicBoolean();
		override.set(false);
	}
	
	protected synchronized void afterTailWrite() {
		notify();
	}
	
	private void tailReadDriver() {
		if (connetctionAlive.get()) {
			while (!serverToClientQueue.isEmpty()) {
				String next = serverToClientQueue.poll();
				sendToClient(next);
			}
		}
	}
	
	/**
	 * to be overridden by the class extending MessagingInterface
	 * this function must process an atomic message string (i.e.
	 * writing it to a socket)
	 * This function is actually driven by the Head-side, which
	 * decides when to transmit
	 */
	public void sendToClient(String singleMessage) {
		// to be overridden
	}
	
	/**
	 * to be overridden by the class extending MessagingInterface
	 * this function must append one or more message strings to
	 * receiveQueue, and finally call afterTailWrite()
	 * Calling said function is MANDATORY
	 */
	public void receiveFromClient() {
		// to be overridden
		// must call afterTailWrite()
		afterTailWrite();
	}

	public void setConnectionAlive() {
		connetctionAlive.set(true);
	}

	public void setConnectionDead() {
		connetctionAlive.set(false);
	}

	public void writeToClient(String message) {
		serverToClientQueue.offer(message);
		tailReadDriver();
	}
	
	/**
	 * in this setup, headRead() won't return at all until
	 * a valid input is received
	 */
	public synchronized String readFromClient() {
		while (true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			
			if (override.get()) {
				override.set(false);
				return defaultOption;
			}
			else {
				while (!clientToServerQueue.isEmpty()) {
					String next = clientToServerQueue.poll();
					if (context == null || context.isEmpty()) {
						return next;
					}
					else if (context.contains(next) && next!=null) {
						return next;
					}
				}
			}
		}
	}

	public void setContext(List<String> context) {
		this.context = context;
	}

	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
	}

	public synchronized void overrideDefaultOption() {
		override.set(true);
		notify();
	}
	
}
