package it.escape.client.controller.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
import it.escape.client.view.gui.BindUpdaterInterface;
import it.escape.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * Updates the swing interface when a new network message arrives
 * TODO: add facilities to modify the local state (the model) too,
 * when needed.
 * @author michele
 *
 */
public class UpdaterSwing extends Updater implements Observer, BindUpdaterInterface {
	
	protected static final Logger LOG = Logger.getLogger( UpdaterSwing.class.getName() );

	private UpdaterSwingToDisplayerInterface view;
	
	private boolean readingMotd = false;
	
	private String loadedMotd = "";
	
	public UpdaterSwing() {
		super();
		LogHelper.setDefaultOptions(LOG);
	}

	public void bindView(UpdaterSwingToDisplayerInterface view) {
		this.view = view;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof MessageCarrier) {
			MessageCarrier msg = (MessageCarrier) arg0;
			processMessage(msg.getMessage());
		}
	}

	@Override
	protected void processMessage(String message) {
		Matcher map = setGameMap.matcher(message);
		Matcher startmotd = getMOTDstart.matcher(message);
		Matcher gameStartETA = startInXSeconds.matcher(message);
		Matcher chatMsg = inboundChatMessage.matcher(message);
		Matcher turnEnd = myTurnEnd.matcher(message);
		Matcher turnStart = myTurnStart.matcher(message);
		
		if (!handlingMOTDspecialCase(message)) {
			if (map.matches()) {
				LOG.finer("Setting map to " + map.group(1));
				view.setGameMap(map.group(1));
			} else if (startmotd.matches()) {
				LOG.finer("Server has begun writing motd");
				readingMotd = true;
			} else if (chatMsg.matches()) {
				view.newChatMessage(chatMsg.group(1), chatMsg.group(2));
			} else if (gameStartETA.matches()) {
				view.setTurnStatusString(message);
			} else if (turnEnd.matches()) {
				view.setTurnStatusString("waiting for my turn");
			} else if (turnStart.matches()) {
				view.setTurnStatusString("now is my turn to play");
				// we could do more (i.e. send a visual notification of some sort)
			} 
		}
		
	}

	/**
	 * The Message Of The Day is a special situation, since it's a
	 * text body read in pieces
	 * @param message
	 * @return
	 */
	private boolean handlingMOTDspecialCase(String message) {
		Matcher endmotd = getMOTDend.matcher(message);
		if (readingMotd) {
			if (endmotd.matches()) {
				LOG.finer("Server has stopped writing motd");
				readingMotd = false;
				view.displayServerMOTD(loadedMotd);;
			} else {
				loadedMotd = loadedMotd + "\n" + message;
			}
			return true;
		}
		return false;
	}
}
