package it.escape.launcher.menucontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ActionSetOption implements ActionListener {
	
	private LauncherStateInterface launcherState;
	
	private boolean isNetworkBox;
	
	public ActionSetOption(LauncherStateInterface launcherState, boolean isNetworkBox) {
		this.launcherState = launcherState;
		this.isNetworkBox = isNetworkBox;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JComboBox<String> source = (JComboBox<String>) arg0.getSource();
		String choice = (String) source.getSelectedItem();
		if (isNetworkBox) {
			launcherState.setNetMode(choice);
		} else {
			launcherState.setUiMode(choice);
		}
	}

}
