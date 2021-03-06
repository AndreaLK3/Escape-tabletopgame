package it.escape.core.client.controller;

import it.escape.core.client.connection.rmi.ProxyToServer;
import it.escape.tools.strings.FormatToPattern;
import it.escape.tools.strings.StringRes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelayForRMI extends Relay {
	
	private Pattern rename;
	private Pattern chat;
	private Pattern whoami;
	private Pattern whereami;
	
	private ProxyToServer server;

	public RelayForRMI(ClientChannelInterface communication) {
		super(communication);
		server = (ProxyToServer) communication;
		rename = new FormatToPattern(StringRes.getString("messaging.renameMyself")).convert();
		chat = new FormatToPattern(StringRes.getString("messaging.chat")).convert();
		whoami = new FormatToPattern(StringRes.getString("messaging.whoami")).convert();
		whereami = new FormatToPattern(StringRes.getString("messaging.whereami")).convert();
	}

	@Override
	public void relayMessage(String message) {
		processMessage(message);
	}
	
	/**This method reads the String parameter and invokes 
	 * the corresponding method in the ServerRemote.
	 * (Alternatives: rename, globalChat, whoAmI, whereAmI that use the ServerSocketCore Asynchronous communication,
	 * and setAnswer that is employed to send a String that the ServerSocketCore is waiting for*/
	private void processMessage(String msg) {
		Matcher ren = rename.matcher(msg);
		Matcher cha = chat.matcher(msg);
		Matcher me = whoami.matcher(msg);
		Matcher mypos = whereami.matcher(msg);
		
		if (ren.matches()) {
			server.rename(ren.group(1));
		} else if (cha.matches()) {
			server.globalChat(cha.group(1));
		} else if (me.matches()) {
			server.whoAmI();
		} else if (mypos.matches()) {
			server.whereAmI();
		} else {
			server.setAnswer(msg);
		}
	}

}
