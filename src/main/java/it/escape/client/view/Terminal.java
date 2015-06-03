package it.escape.client.view;

import it.escape.client.controller.Relay;
import it.escape.client.controller.StateManager;
import it.escape.client.controller.TurnInputStates;

import java.io.PrintStream;
import java.util.Scanner;

public class Terminal {
	
	private boolean running = true;
	
	private  Scanner in = new Scanner(System.in);
	
	private PrintStream out = System.out;
	
	private Relay relayRef;
	
	private StateManager stateManager;
	
	private String userInput;
	
	public Terminal (Relay relay, StateManager stateManager) {
		this.relayRef = relay;
		this.stateManager = stateManager;
	}
	
	public void mainLoop() {
		while (running) {
			userInput = in.nextLine();
			if (stateManager.getCurrentState() == TurnInputStates.FREE) {
				relayRef.relayMessage(userInput);
			}
			if (stateManager.getCurrentState() == TurnInputStates.OBJECTCARD) {
				if (relayRef.checkCardsFormat(userInput))
					relayRef.relayMessage(userInput);
				else 
					out.println("Format error");
			}
			if (stateManager.getCurrentState() == TurnInputStates.POSITION) {
				if (relayRef.checkYesNoFormat(userInput))
					relayRef.relayMessage(userInput);
				else 
					out.println("Format error");
			}

		}
	}
	
	
}
