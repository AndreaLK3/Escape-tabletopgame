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
	
	protected Queue<String> transmitQueue;
	protected Queue<String> reciveQueue;
	
	private List<String> context = null;
	
	private String defaultOption;
	
	private AtomicBoolean connetctionAlive;
	
	private AtomicBoolean override;
	
	public MessagingInterface() {
		transmitQueue = new ConcurrentLinkedQueue<String>();
		reciveQueue = new ConcurrentLinkedQueue<String>();
		override = new AtomicBoolean();
		connetctionAlive = new AtomicBoolean();
		override.set(false);
	}
	
	protected synchronized void afterTailWrite() {
		notify();
	}
	
	private void tailReadDriver() {
		if (connetctionAlive.get()) {
			while (!transmitQueue.isEmpty()) {
				String next = transmitQueue.poll();
				tailRead(next);
			}
		}
	}

	public void tailRead(String singleMessage) {
		// to be overridden
	}

	public void tailWrite() {
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

	public void headWrite(String message) {
		transmitQueue.offer(message);
		tailReadDriver();
	}
	
	/**
	 * in this setup, headRead() won't return at all until
	 * a valid input is received
	 */
	public synchronized String headRead() {
		while (true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			
			if (override.get()) {
				return defaultOption;
			}
			else {
				while (!reciveQueue.isEmpty()) {
					String next = reciveQueue.poll();
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
