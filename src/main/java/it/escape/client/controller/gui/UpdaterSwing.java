package it.escape.client.controller.gui;

import it.escape.client.controller.MessageCarrier;
import it.escape.client.controller.Updater;
import it.escape.client.graphics.BindUpdaterInterface;
import it.escape.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.regex.Matcher;

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
		
		if (map.matches()) {
			LOG.finer("Setting map to " + map.group(1));
			view.setGameMap(map.group(1));
		}
	}

}
