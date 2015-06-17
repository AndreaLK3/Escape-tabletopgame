package it.escape.launcher.menucontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ActionAcceptNewPortNumber implements ActionListener {
	
	private JTextField field;
	
	private LauncherLocalSettings locals;
	
	private Pattern validInt = Pattern.compile("[0-9]+");
	
	private static final int minimumPort = 1;
	
	private static final int maximumPort = 65535;

	public ActionAcceptNewPortNumber(JTextField field, LauncherLocalSettings locals) {
		this.field = field;
		this.locals = locals;
	}
	
	public void actionPerformed(ActionEvent e) {
		String newPort = field.getText();
		if (validInt.matcher(newPort).matches()) {
			try {
				int newPortInt = Integer.parseInt(newPort);
				if (newPortInt < minimumPort || newPortInt > maximumPort) {
					throw new NumberFormatException();
				}
				locals.setServerPort(newPortInt);
				confirmed(newPortInt < 1024);
			} catch (NumberFormatException e1) {
				outOfRange();
				field.setText("" + locals.getServerPort());
			}
		} else {
			invalidInt();
			field.setText("" + locals.getServerPort());
		}
	}
	
	private void confirmed(boolean warn) {
		String message = "";
		if (warn) {
			message = "\nkeep in mind that opening ports lower than 1024\n"
					+ "may require root privilege on some systems";
		}
		JOptionPane.showMessageDialog(null,
			    "Port number successfully set to " + locals.getServerPort() + message,
			    "Success",
			    JOptionPane.PLAIN_MESSAGE);
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
