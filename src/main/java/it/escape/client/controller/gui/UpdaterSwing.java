package it.escape.client.controller.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
import it.escape.client.graphics.BindUpdaterInterface;
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
		Matcher motd = getMOTD.matcher(message);
		
		if (map.matches()) {
			LOG.finer("Setting map to " + map.group(1));
			view.setGameMap(map.group(1));
		} else if (motd.matches()) {
			LOG.finer("Received MOTD from server");
			view.displayServerMOTD(motd.group(1));
		}
	}

}
