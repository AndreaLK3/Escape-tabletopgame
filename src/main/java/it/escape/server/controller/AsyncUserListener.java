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

/**
 * The purpose of this object is to asynchronously monitor all the messages
 * coming from a specific user, and act accordingly.
 * Since the core gameplay is highly synchronous, this class only
 * handles secondary aspect of the game (changing name, chatting with others)
 * @author michele
 *
 */
public class AsyncUserListener implements Observer{
	
	private static final Logger LOG = Logger.getLogger( AsyncUserListener.class.getName() );
	
	private Pattern rename;
	private Pattern chat;
	private Pattern whoami;
	private Pattern whereami;
	
	private PlayerActionInterface subject;
	
	private UserMessagesReporter privateUMR;
	
	private Announcer announcer;
	
	private GameMaster gameMaster;
	
	public AsyncUserListener(PlayerActionInterface subject, Announcer announcer, UserMessagesReporter myUMR, GameMaster gameMaster) {
		LogHelper.setDefaultOptions(LOG);
		this.subject = subject;
		this.announcer = announcer;
		this.privateUMR = myUMR;
		this.gameMaster = gameMaster;
		rename = new FormatToPattern(StringRes.getString("messaging.renameMyself")).convert();
		chat = new FormatToPattern(StringRes.getString("messaging.chat")).convert();
		whoami = new FormatToPattern(StringRes.getString("messaging.whoami")).convert();
		whereami = new FormatToPattern(StringRes.getString("messaging.whereami")).convert();
		
		LOG.fine("Async listener for user " + subject.getName() + " has been  created");
	}
	
	private void processMessage(String msg) {
		Matcher ren = rename.matcher(msg);
		Matcher cha = chat.matcher(msg);
		Matcher me = whoami.matcher(msg);
		Matcher mypos = whereami.matcher(msg);
		
		if (ren.matches()) {
			LOG.finer("Rename command detected");
			renameProcedure(ren);
		} else if (cha.matches()) {
			LOG.finer("Chat message detected");
			chatProcedure(cha);
		} else if (me.matches()) {
			LOG.finer("Whoami message detected");
			whoAmIProcedure();
		} else if (mypos.matches()) {
			LOG.finer("Whereami message detected");
			whereAmIProcedure();
		} // other cases
	}
	
	private void whereAmIProcedure() {
		privateUMR.relayMessage(String.format(
				StringRes.getString("messaging.hereYouAre"),
				gameMaster.getPlayerPosition(subject)));
	}

	private void chatProcedure(Matcher match) {
		String message = match.group(1).trim();  // also remove initial/trailing blank spaces
		announcer.announceChatMessage(subject, message);
	}
	
	private void renameProcedure(Matcher match) {
		String newname = match.group(1);
		if (!gameMaster.hasPlayerNamed(newname)) {
			announcer.announcePlayerRename(subject.getName(),newname);
			subject.changeName(newname);
		}
		
	}
	
	private void whoAmIProcedure() {
		privateUMR.relayMessage(String.format(
				StringRes.getString("messaging.whoYouAre"),
				subject.getName()));
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof AsyncMessagingObservable) {
			AsyncMessagingObservable msg = (AsyncMessagingObservable) arg0;
			processMessage(msg.getMessage());
		}
	}

}
