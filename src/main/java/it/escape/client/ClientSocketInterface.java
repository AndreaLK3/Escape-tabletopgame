package it.escape.client;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ClientSocketInterface extends Observable implements Runnable {
	
	private final static int PORTNUMBER = 1331;
	
	private final String SERVERIP;
	
	private Scanner in;
	
	private PrintStream out;
	
	private Socket clientSocket;
	
	private boolean running;
	
	private MessageCarrier messaging;
	
	
	public ClientSocketInterface(String ipAddress) throws UnknownHostException, IOException {
		SERVERIP = ipAddress;
		clientSocket = new Socket(SERVERIP, PORTNUMBER);
		in = new Scanner(clientSocket.getInputStream());
		out = new PrintStream(clientSocket.getOutputStream());
		messaging = new MessageCarrier();
		running = true;
	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		messaging.addObserver(o);
	}

	public void mainLoop() {
		while (running) {
			try {
				String read = in.nextLine();
				messaging.newMessage(read);
			} catch (NoSuchElementException e) {  // detect disconnection
				disconnected();
			}
		}
		
	}
	
	private void disconnected() {
		try {
			clientSocket.close();
			//log.info("Closed connection to " + clientSocket.getInetAddress().toString());
		} catch (IOException e) {
			System.out.println("Error: cannot close the connection");
		}
	}
	
	public void killConnection() {
		out.close();
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
	}

	@Override
	public void run() {
		mainLoop();		
	}
	
	    /*  try {
	         Socket c = new Socket("localhost",8888);
	         BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
	            c.getOutputStream()));
	         BufferedReader r = new BufferedReader(new InputStreamReader(
	            c.getInputStream()));
	         String m = null;
	         while ((m=r.readLine())!= null) {
	            out.println(m);
	            m = in.readLine();
	            w.write(m,0,m.length());
	            w.newLine();
	            w.flush();
	         }
	         w.close();
	         r.close();
	         c.close();
	      } catch (IOException e) {
	         System.err.println(e.toString());
	      }
	   }*/
}
