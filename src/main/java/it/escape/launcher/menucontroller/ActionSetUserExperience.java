package it.escape.launcher.menucontroller;

import it.escape.launcher.LauncherState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ActionSetUserExperience implements ActionListener {
	private LauncherState launcherState;

	public ActionSetUserExperience(LauncherState launcherState) {
		this.launcherState = launcherState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
        String selected = (String)cb.getSelectedItem();
        
        launcherState.setUiMode(selected);
	}
}
