package it.escape.server.view;

import static org.junit.Assert.*;
import it.escape.server.controller.TestingAnnouncerObserver;

import java.util.Arrays;

import org.junit.Test;

public class MessagingInterfaceTest {

	@Test
	public void testSynchronousSend() {
		TestingInterface iface = new TestingInterface();
		
		String test = "test message";
		iface.writeToClient(test);
		assertEquals(test, iface.getLast_message());
		
		test = "test message 123456";
		iface.writeToClient(test);
		assertEquals(test, iface.getLast_message());
		
		test = "test message\n\r\n 123456";
		iface.writeToClient(test);
		assertEquals(test, iface.getLast_message());
		
		test = "test message çò#èé€";
		iface.writeToClient(test);
		assertEquals(test, iface.getLast_message());
	}
	
	@Test
	public void testSynchronousReceive() {
		String wanted = "my precious message";
		String garbage = "baka";
		String ret;
		TestingInterface iface = new TestingInterface();
		TestInterfaceFeeder feeder = new TestInterfaceFeeder(garbage, wanted, iface);
		Thread feederThread = new Thread(feeder);
		iface.setContext(Arrays.asList(wanted));
		feederThread.start();
		ret = iface.readFromClient();
		feeder.stop();
		try {
			feederThread.join();
		} catch (InterruptedException e) {
		}
		assertEquals(wanted, ret);
	}

	@Test
	public void testAsynchronousReceive() {
		TestingInterface iface = new TestingInterface();
		TestingInterfaceObserver observer = new TestingInterfaceObserver();
		iface.addObserver(observer);
		String message = "ciao";
		iface.receiveFromClient(message);
		assertEquals(message, observer.getLast_message());
		message = "dsnadnsadn nsdnsd nsdanjasjLLKDUIE";
		iface.receiveFromClient(message);
		assertEquals(message, observer.getLast_message());
		message = "canta o diva del pelide achille";
		iface.receiveFromClient(message);
		assertEquals(message, observer.getLast_message());
		
	}
}
