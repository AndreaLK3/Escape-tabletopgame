package it.escape.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientInitializer {
	
	private static ClientSocketInterface connection;
	
	public static void main(String[] args) {
		String ipaddress = "127.0.0.1";
		try {
			connection = new ClientSocketInterface(ipaddress);
			new Thread(connection).start();
			
		} catch (UnknownHostException e) {
			System.out.println("Error: unknown host");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
