package it.escape.core.server.controller;

import it.escape.MessageCarrier;
import it.escape.core.server.controller.game.actions.PlayerActionInterface;
import it.escape.core.server.model.Announcer;
import it.escape.tools.strings.FormatToPattern;
import it.escape.tools.strings.StringRes;
import it.escape.tools.utils.LogHelper;

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
		
		String warn = "";
		if (myUMR instanceof UserMessagesReporterRMI) {
			warn = "\nThis feature won't actually be used, as we are on RMI";
		}
		LOG.fine("Async listener for user " + subject.getName() + " has been  created" + warn);
	}
	
	private void processMessage(String msg) {
		Matcher ren = rename.matcher(msg);
		Matcher cha = chat.matcher(msg);
		Matcher me = whoami.matcher(msg);
		Matcher mypos = whereami.matcher(msg);
		
		if (ren.matches()) {
			LOG.finer("Rename command detected");
			renameProcedure(ren.group(1));
		} else if (cha.matches()) {
			LOG.finer("Chat message detected");
			chatProcedure(cha.group(1).trim());
		} else if (me.matches()) {
			LOG.finer("Whoami message detected");
			whoAmIProcedure();
		} else if (mypos.matches()) {
			LOG.finer("Whereami message detected");
			whereAmIProcedure();
		} // other cases
	}
	
	private void whereAmIProcedure() {
		privateUMR.reportMyUserPosition(
				gameMaster.getPlayerPosition(subject));
	}

	private void chatProcedure(String message) {
		gameMaster.globalChat(subject, message);
	}
	
	private void renameProcedure(String newname) {
		gameMaster.renamePlayer(subject, newname);	
	}
	
	private void whoAmIProcedure() {
		privateUMR.relayMessage(String.format(
				StringRes.getString("messaging.whoYouAre"),
				subject.getName()));
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof MessageCarrier) {
			MessageCarrier msg = (MessageCarrier) arg0;
			processMessage(msg.getMessage());
		}
	}

}
