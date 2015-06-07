package it.escape.launcher.menucontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ActionSetNetMode implements ActionListener {
	
	private LauncherStateInterface launcherState;

	public ActionSetNetMode(LauncherStateInterface launcherState) {
		this.launcherState = launcherState;
	}

	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
        String selected = (String)cb.getSelectedItem();
        
        launcherState.setNetMode(selected);
	}

}
