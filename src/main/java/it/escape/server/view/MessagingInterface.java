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
	
	private Queue<String> transmitQueue;
	private Queue<String> reciveQueue;
	
	private List<String> context;
	
	private String defaultOption;
	
	private AtomicBoolean connetctionAlive;
	
	private AtomicBoolean override;
	
	public MessagingInterface() {
		transmitQueue = new ConcurrentLinkedQueue<String>();
		reciveQueue = new ConcurrentLinkedQueue<String>();
		override.set(false);
	}
	
	protected void afterTailWrite() {
		notify();
	}

	public void tailRead() {
		// to be overridden
	}

	public void tailWrite(String message) {
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

	public String headRead() {
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
				if (context.contains(next)) {
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

	public void overrideDefaultOption() {
		override.set(true);
		notify();
	}
	
}
