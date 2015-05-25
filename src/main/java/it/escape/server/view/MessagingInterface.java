package it.escape.server.view;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

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

	public void tailRead() {
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
		tailRead();
	}

	public synchronized String headRead() {
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

		return "nothing";
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
