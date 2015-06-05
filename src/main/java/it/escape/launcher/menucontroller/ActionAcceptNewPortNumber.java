package it.escape.launcher.menucontroller;

import it.escape.GlobalSettings;
import it.escape.launcher.LauncherState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ActionAcceptNewPortNumber implements ActionListener {
	
	private JTextField field;
	
	private Pattern validInt = Pattern.compile("[0-9]+");
	
	private static final int minimumPort = 1;
	
	private static final int maximumPort = 65535;

	public ActionAcceptNewPortNumber(JTextField field) {
		this.field = field;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String newPort = field.getText();
		if (validInt.matcher(newPort).matches()) {
			try {
				int newPortInt = Integer.parseInt(newPort);
				if (newPortInt < minimumPort || newPortInt > maximumPort) {
					throw new NumberFormatException();
				}
				GlobalSettings.setServerPort(newPortInt);
			} catch (NumberFormatException e1) {
				outOfRange();
				field.setText("" + GlobalSettings.getServerPort());
			}
		} else {
			invalidInt();
			field.setText("" + GlobalSettings.getServerPort());
		}
	}
	
	private void outOfRange() {
		JOptionPane.showMessageDialog(null,
			    "Please enter an integer in the interval [" + minimumPort + "," + maximumPort + "]",
			    "Error!",
			    JOptionPane.WARNING_MESSAGE);
	}

	private void invalidInt() {
		JOptionPane.showMessageDialog(null,
			    "Please enter an integer number",
			    "Error!",
			    JOptionPane.ERROR_MESSAGE);
	}
}