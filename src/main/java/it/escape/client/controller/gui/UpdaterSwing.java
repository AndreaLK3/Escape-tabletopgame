package it.escape.client.controller.gui;

import it.escape.client.controller.Updater;
import it.escape.client.graphics.BindUpdaterInterface;

import java.util.Observable;
import java.util.Observer;

public class UpdaterSwing extends Updater implements Observer, BindUpdaterInterface {

	private UpdaterSwingToDisplayerInterface view;
	
	public void bindView(UpdaterSwingToDisplayerInterface view) {
		this.view = view;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processMessage(String message) {
		// TODO Auto-generated method stub
		
	}

}
