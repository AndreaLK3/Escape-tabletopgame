package it.escape.server.view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sun.jndi.cosnaming.CNNameParser;

import it.escape.server.controller.MessagingHead;

/**
 * MessagingInterface manages a single per-player communication channel.
 * The "Head" side is the server, the "Tail" side is a socket connection
 * to the client (the user).
 * Server-to-client messages are instantly submitted,
 * Client-to-server messages are accessible through two different mechanisms:
 * (1) The first one, which emulate the behavior of a command line interface,
 * and is intended for *synchronous* user-commands during the turn sequence:
 *   When a new message arrives:
 *     if the server is not reading, the incoming message is stored
 *     if the server is reading, the queue is parsed, and the first valid
 *       message is returned
 *   The reading operation is blocking, but can be un-stuck by overrideDefaultOption()
 * (2) The second one, a simple Observable implementation, in which the Observers
 * are instantly and *asynchronusly* notified whenever a new message arrives.
 * The new message does not undergo any validations, and remains in the queue, so
 * that the (1) mechanism can still access it.
 * This method is intended for game-wide messages, such as changing a player's name
 * and/or chatting with other players
 * 
 * @author michele, andrea
 */
public class MessagingInterface extends Observable implements MessagingHead, MessagingTail {
	
	protected Queue<String> serverToClientQueue;
	protected Queue<String> clientToServerQueue;
	
	private List<String> context = null;
	
	private String defaultOption;
	
	private AtomicBoolean connectionAlive;
	
	private AtomicBoolean override;
	
	// MessagingInterface is simply a proxy to this Observable
	private AsyncMessagingObservable asyncInterface;
	
	public MessagingInterface() {
		serverToClientQueue = new ConcurrentLinkedQueue<String>();
		clientToServerQueue = new ConcurrentLinkedQueue<String>();
		override = new AtomicBoolean();
		connectionAlive = new AtomicBoolean();
		asyncInterface = new AsyncMessagingObservable();
		override.set(false);
	}
	
	protected synchronized void afterTailWrite() {
		asyncInterface.newMessage(clientToServerQueue.peek());
		notify();
	}
	
	private void tailReadDriver() {
		if (connectionAlive.get()) {
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
		connectionAlive.set(true);
	}

	public void setConnectionDead() {
		connectionAlive.set(false);
	}
	
	public boolean isConnectionAlive() {
		return connectionAlive.get();
	}
	
	/**
	 * send a message from the server to the client
	 */
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
	
	/**
	 * Set the context, a list of string which are acceptable
	 * by the readFromClient() function;
	 * Messages not belonging to the context are discarded without
	 * further processing.
	 * If the context is empty or null, any message will be accepted
	 */
	public void setContext(List<String> context) {
		this.context = context;
	}
	
	/**
	 * Set a default string to be returned.
	 * The default string will be returned only when
	 * overrideDefaultOption() is called
	 */
	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
	}

	public synchronized void overrideDefaultOption() {
		override.set(true);
		notify();
	}

	@Override
	public synchronized void addObserver(Observer o) {
		asyncInterface.addObserver(o);
	}
	
}
