package it.escape.client;

import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientSocketInterface {
	
	private final static int PORTNUMBER = 1331;
	
	private final String SERVERIP;
	
	private Scanner in;
	
	private PrintStream out;
	
	private Socket clientSocket;
	
	private boolean running;
	
	
	public ClientSocketInterface(String ipAddress) throws UnknownHostException, IOException {
		SERVERIP = ipAddress;
		clientSocket = new Socket(SERVERIP, PORTNUMBER);
		in = new Scanner(clientSocket.getInputStream());
		out = new PrintStream(clientSocket.getOutputStream());
		running = true;
	}

	public void mainLoop() {
	while (running) {
		try {
			String read = in.nextLine();
		} catch (NoSuchElementException e) {  // detect disconnection
			//hasDisconnected();
		}
	}
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

