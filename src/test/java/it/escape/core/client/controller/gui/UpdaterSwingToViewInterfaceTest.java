package it.escape.core.client.controller.gui;

import static org.junit.Assert.*;
import it.escape.core.client.model.ModelForGUI;
import it.escape.core.server.model.game.cards.ObjectCard;
import it.escape.core.server.model.game.cards.objectcards.SedativesCard;

import org.junit.Test;

public class UpdaterSwingToViewInterfaceTest {
	
	@Test
	public void cardNaming() {
		ModelForGUI model = new ModelForGUI();
		UpdaterSwing updater = new UpdaterSwing(model);
		ObjectCard sedatives = new SedativesCard();
		String classname = sedatives.getClass().getSimpleName();
		assertEquals("SedativesCard", classname);
		
		String returned = updater.getCardGUIKey(classname);
		assertEquals("sedatives", returned);
	}

}
