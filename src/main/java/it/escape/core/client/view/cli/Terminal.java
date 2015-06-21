package it.escape.core.client.view.cli;

import it.escape.core.client.connection.DisconnectedCallbackInterface;
import it.escape.core.client.controller.Relay;
import it.escape.core.client.controller.cli.UpdaterCLItoTerminalInterface;
import it.escape.tools.strings.StringRes;

import java.io.PrintStream;
import java.util.Scanner;

public class Terminal implements DisconnectedCallbackInterface, UpdaterCLItoTerminalInterface {
	
	private boolean running = true;
	
	private Scanner in;
	
	private PrintStream out;
	
	private Relay relayRef;
	
	private StateManagerCLI stateManager;
	
	private String userInput;
	
	private String prompt;
	
	public Terminal (Relay relay, StateManagerCLI stateManager, Scanner in, PrintStream out) {
		this.in = in;
		this.out = out;
		this.relayRef = relay;
		this.stateManager = stateManager;
		prompt = "";
	}
	
	public void bindRelay(Relay relay) {
		this.relayRef = relay;
	}
	
	private void buildPrompt() {
		String whoseTurn;
		
		if (stateManager.isMyTurn()) {
			whoseTurn = StringRes.getString("client.text.prompt.myturn");
		} else {
			whoseTurn = StringRes.getString("client.text.prompt.someoneElsesTurn");
		}
		
		prompt = String.format(
				StringRes.getString("client.text.prompt"),
				whoseTurn,
				stateManager.getCurrentState().getPrompt());
	}
	
	/**
	 * draw a nice prompt dos-style
	 */
	private void printPrompt() {
		buildPrompt();
		out.print(prompt);
	}
	
	public void mainLoop() {
		while (running) {
			refreshPrompt();
			userInput = in.nextLine();
			
			boolean localcommand = checkAndRunLocalCommands();
			if (running && !localcommand) {
				checkAndSend();
			}
		}
		out.println(); // newline, nice for shell users
	}
	
	private void checkAndSend() {
		
		if (relayRef.checkFreeMessage(userInput)) {  // chat, rename and other 'free' messages can always be sent
			relayRef.relayMessage(userInput);
		} else if (stateManager.getCurrentState() == TurnInputStates.FREE) {  // free state, any message can be sent
			relayRef.relayMessage(userInput);
		} else if (stateManager.getCurrentState() == TurnInputStates.OBJECTCARD) {  // predisposed to read an object card key
			if (relayRef.checkCardsFormat(userInput)) {
				relayRef.relayMessage(userInput);
				stateManager.setFreeState();
			} else {
				out.println(String.format(
						StringRes.getString("client.text.error.format.objectcard"),
						StringRes.getString("client.text.validCards")));
			}
		} else if (stateManager.getCurrentState() == TurnInputStates.POSITION) {  // predisposed to read a coordinate
			if (relayRef.checkPositionFormat(userInput)) {
				relayRef.relayMessage(userInput);
				stateManager.setFreeState();
			} else {
				out.println(StringRes.getString("client.text.error.format.position"));
			}	
		} else if (stateManager.getCurrentState() == TurnInputStates.YES_NO) {  // predisposed to read yes/no
			if (relayRef.checkYesNoFormat(userInput)) {
				relayRef.relayMessage(userInput);
				stateManager.setFreeState();
			} else {
				out.println("Format error");
			}	
		} 
	}
	
	/**
	 * If the provided command can be run locally (i.e. "disconnect"),
	 * run it directly. Returns true if such circumstances did take place
	 * @return
	 */
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
	}
	
	private void cleanPromptAndPrintln(String message) {
		cleanPromptAndPrint(message);
		out.println();  // newline
	}
	
	public void refreshPrompt() {
		buildPrompt();
		cleanPromptAndPrint(prompt);
	}
	
	public void visualizeMessage(String message) {
		cleanPromptAndPrintln(message);
		printPrompt();  // re-create the prompt string
	}

	public void disconnected() {
		visualizeMessage(StringRes.getString("client.text.disconnectByServer"));
		running = false;
	}
}
