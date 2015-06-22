package it.escape.core.client;

import static org.junit.Assert.*;

import java.util.Scanner;

import it.escape.core.client.controller.cli.UpdaterCLI;
import it.escape.core.client.view.cli.StateManagerCLI;
import it.escape.core.client.view.cli.Terminal;
import it.escape.core.client.view.cli.TurnInputStates;
import it.escape.tools.strings.StringRes;

import org.junit.Test;

/**
 * Manually feed strings into the updater, build the prompt string,
 * and check that the prompt looks the way we expected
 * @author michele
 *
 */
public class ClientTerminalTest {

	private UpdaterCLI updater;
	
	private Terminal view;
	
	@Test
	public void test() {
		StateManagerCLI stateManager = new StateManagerCLI();
		view = new Terminal(null, stateManager, new Scanner(System.in), System.out);
		updater = new UpdaterCLI(stateManager, view);
		
		testTurnStart();
		testObjectCard();
		testPosition();
		testYesNo();
		testNotMyTurn();
	}

	private void testTurnStart() {
		updater.processMessage(StringRes.getString("messaging.hail.player"));
		String expectedPrompt = String.format(
				StringRes.getString("client.text.prompt"),
				StringRes.getString("client.text.prompt.myturn"),
				TurnInputStates.FREE.getPrompt());
		assertEquals(expectedPrompt, view.buildAndGetPrompt());
	}
	
	private void testObjectCard() {
		updater.processMessage(StringRes.getString("messaging.askWhichObjectCard"));
		String expectedPrompt = String.format(
				StringRes.getString("client.text.prompt"),
				StringRes.getString("client.text.prompt.myturn"),
				TurnInputStates.OBJECTCARD.getPrompt());
		assertEquals(expectedPrompt, view.buildAndGetPrompt());
	}
	
	private void testPosition() {
		updater.processMessage(StringRes.getString("messaging.askForPosition"));
		String expectedPrompt = String.format(
				StringRes.getString("client.text.prompt"),
				StringRes.getString("client.text.prompt.myturn"),
				TurnInputStates.POSITION.getPrompt());
		assertEquals(expectedPrompt, view.buildAndGetPrompt());
	}
	
	private void testYesNo() {
		updater.processMessage(String.format(
				StringRes.getString("messaging.askBinaryChoice"),
				"yes",
				"no"));
		String expectedPrompt = String.format(
				StringRes.getString("client.text.prompt"),
				StringRes.getString("client.text.prompt.myturn"),
				TurnInputStates.YES_NO.getPrompt());
		assertEquals(expectedPrompt, view.buildAndGetPrompt());
	}
	
	private void testNotMyTurn() {
		updater.processMessage(StringRes.getString("messaging.farewell"));
		String expectedPrompt = String.format(
				StringRes.getString("client.text.prompt"),
				StringRes.getString("client.text.prompt.someoneElsesTurn"),
				TurnInputStates.FREE.getPrompt());
		assertEquals(expectedPrompt, view.buildAndGetPrompt());
	}
}
