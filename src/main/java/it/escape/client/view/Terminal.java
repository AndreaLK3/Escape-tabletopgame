package it.escape.client.view;

import it.escape.client.DisconnectedCallbackInterface;
import it.escape.client.controller.Relay;
import it.escape.client.controller.StateManager;
import it.escape.client.controller.TurnInputStates;
import it.escape.strings.StringRes;

import java.io.PrintStream;
import java.util.Scanner;

public class Terminal implements DisconnectedCallbackInterface {
	
	private boolean running = true;
	
	private  Scanner in = new Scanner(System.in);
	
	private PrintStream out = System.out;
	
	private Relay relayRef;
	
	private StateManager stateManager;
	
	private String userInput;
	
	private String prompt;
	
	public Terminal (Relay relay, StateManager stateManager) {
		this.relayRef = relay;
		this.stateManager = stateManager;
	}
	
	/**
	 * draw a nice prompt dos-style
	 */
	private void printPrompt() {
		String whoseTurn;
		
		if (stateManager.isMyTurn()) {
			whoseTurn = "My turn";
		} else {
			whoseTurn = "Someone else's turn";
		}
		
		prompt = String.format(
				StringRes.getString("client.text.prompt"),
				whoseTurn,
				stateManager.getCurrentState().getPrompt());
		
		out.print(prompt);
	}
	
	public void mainLoop() {
		while (running) {
			printPrompt();
			userInput = in.nextLine();
			
			boolean localcommand = checkAndRunLocalCommands();
			if (running && !localcommand) {
				checkAndSend();
			}
		}
	}
	
	private void checkAndSend() {
		if (stateManager.getCurrentState() == TurnInputStates.FREE) {
			relayRef.relayMessage(userInput);
		} else if (stateManager.getCurrentState() == TurnInputStates.OBJECTCARD) {
			if (relayRef.checkCardsFormat(userInput)) {
				relayRef.relayMessage(userInput);
				stateManager.setFreeState();
			} else {
				out.println("Format error");
			}
		} else if (stateManager.getCurrentState() == TurnInputStates.POSITION) {
			if (relayRef.checkPositionFormat(userInput)) {
				relayRef.relayMessage(userInput);
				stateManager.setFreeState();
			} else {
				out.println("Format error");
			}	
		} else if (stateManager.getCurrentState() == TurnInputStates.YES_NO) {
			if (relayRef.checkYesNoFormat(userInput)) {
				relayRef.relayMessage(userInput);
				stateManager.setFreeState();
			} else {
				out.println("Format error");
			}	
		}
	}
	
	private boolean checkAndRunLocalCommands() {
		if (userInput.equals(StringRes.getString("client.commands.disconnect"))) {
			running = false;
			relayRef.disconnectNow();
			return true;
		} else if (userInput.equals(StringRes.getString("client.commands.printhelp"))) {
			visualizeMessage(StringRes.getString("client.help"));
			return true;
		}
		return false;
	}
	
	private void cleanPromptAndPrint(String message) {
		int oldPromptLength = prompt.length();
		int newMessageLength = message.length();
		
		out.print("\r");  // go to the beginning of the line
		
		out.print(message);  // print the message
		
		if (newMessageLength < oldPromptLength) {  
			for (int i=0; i < (oldPromptLength - newMessageLength); i++) {
				out.print(" ");  // cover up remaining 'old' characters
			}
		}
		
		out.println();  // newline
	}
	
	public void visualizeMessage(String message) {
		cleanPromptAndPrint(message);
		printPrompt();  // re-create the prompt string
	}

	public void disconnected() {
		visualizeMessage("Disconnected by server; press enter to quit.");
		running = false;
	}
}
