package it.escape.server.controller;

import it.escape.server.controller.game.actions.PlayerActionInterface;
import it.escape.server.model.game.Announcer;
import it.escape.server.view.AsyncMessagingObservable;
import it.escape.strings.FormatToPattern;
import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsyncUserListener implements Observer{
	
	private static final Logger LOG = Logger.getLogger( AsyncUserListener.class.getName() );
	
	private Pattern rename;
	private Pattern chat;
	
	private PlayerActionInterface subject;
	
	private Announcer announcer;
	
	private boolean renamedOnce;  // we allow a player to change name only once per game
	
	public AsyncUserListener(PlayerActionInterface subject, Announcer announcer) {
		LogHelper.setDefaultOptions(LOG);
		renamedOnce = false;
		this.subject = subject;
		this.announcer = announcer;
		rename = new FormatToPattern(StringRes.getString("messaging.renameMyself")).convert();
		chat = new FormatToPattern(StringRes.getString("messaging.chat")).convert();
		
		LOG.fine("Async listener for user " + subject.getName() + " has been  created");
	}
	
	private void processMessage(String msg) {
		Matcher ren = rename.matcher(msg);
		Matcher cha = chat.matcher(msg);
		if (ren.matches()) {
			LOG.finer("Rename command detected");
			if (!renamedOnce) {
				renameProcedure(ren);
			}
		} else if (cha.matches()) {
			LOG.finer("Chat message detected");
			chatProcedure(cha);
		} // other cases
	}
	
	private void chatProcedure(Matcher match) {
		String message = match.group(1).trim();  // also remove initial/trailing blank spaces
		announcer.announceChatMessage(subject, message);
	}
	
	private void renameProcedure(Matcher match) {
		renamedOnce = true;
		String newname = match.group(1);
		announcer.announcePlayerRename(subject.getName(),newname);
		subject.changeName(newname);
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof AsyncMessagingObservable) {
			AsyncMessagingObservable msg = (AsyncMessagingObservable) arg0;
			processMessage(msg.getMessage());
		}
	}

}
