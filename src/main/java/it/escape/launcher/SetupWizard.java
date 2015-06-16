package it.escape.launcher;

import javax.swing.JOptionPane;

import it.escape.launcher.menucontroller.LauncherStateInterface;
import it.escape.strings.StringRes;

public class SetupWizard {
	
	private String flow;
	
	private String subFlow;
	
	private LauncherStateInterface state;
	
	private String[] chooseflow = {
			StringRes.getString("launcher.wizard.flow.client"),
			StringRes.getString("launcher.wizard.flow.server"),
			StringRes.getString("launcher.wizard.flow.both")};
	
	private String[] netmodesServer = {
			StringRes.getString("launcher.option.netmode.socket"),
			StringRes.getString("launcher.option.netmode.RMI"),
			StringRes.getString("launcher.option.netmode.combo")};
	
	private String[] netmodesClient = {
			StringRes.getString("launcher.option.netmode.socket"),
			StringRes.getString("launcher.option.netmode.RMI")};
	
	private String[] netmodes;
	
	private String[] experience = {
			StringRes.getString("launcher.option.experience.graphical"),
			StringRes.getString("launcher.option.experience.textual")};

	public SetupWizard(LauncherStateInterface state) {
		this.state = state;
		flow = chooseflow[0];
		subFlow = "home";
		mainSequence();
	}
	
	private void mainSequence() {
		welcome();
		setFlow();
		if (flow.equals(StringRes.getString("launcher.wizard.flow.client")) || 
				flow.equals(StringRes.getString("launcher.wizard.flow.both"))) {
			subFlow = StringRes.getString("launcher.wizard.flow.client");
			netmodes = netmodesClient;
			setUX();
			setNet();
		}
		if (flow.equals(StringRes.getString("launcher.wizard.flow.server")) || 
				flow.equals(StringRes.getString("launcher.wizard.flow.both"))) {
			subFlow = StringRes.getString("launcher.wizard.flow.server");
			netmodes = netmodesServer;
			setUX();
			setNet();
		}
		goodbye();
	}
	
	private void welcome() {
		JOptionPane.showMessageDialog(null,
			    "Welcome to the user-friendly setup wizard, press OK","Welcome", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void goodbye() {
		StringBuilder bye = new StringBuilder();
		bye.append("The operation ");
		bye.append(flow);
		bye.append(", was completed with success.\n");
		bye.append("Your changes will remain saved until you restart the game");
		JOptionPane.showMessageDialog(null,
			    bye.toString(),"Finished", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void setFlow() {
		String defaultOpt = chooseflow[0];
		String ans = selectOption(chooseflow, defaultOpt, "preparation", "Select the components you wish to configure:");
		if ((ans != null) && (ans.length() > 0)) {
			flow = ans;
		}
	}
	
	private void setUX() {
		String defaultOpt = state.getUiMode();
		String ans = selectOption(experience, defaultOpt, "user experience", "Select how do you wish to interact with the game:");
		if ((ans != null) && (ans.length() > 0)) {
			state.setUiMode(ans);
		}
	}
	
	private void setNet() {
		String defaultOpt = state.getNetMode();
		String ans = selectOption(netmodes, defaultOpt, "networking", "Select how do you wish to handle network connectivity:");
		if ((ans != null) && (ans.length() > 0)) {
			state.setNetMode(ans);
		}
	}
	
	private String selectOption(Object[] options, String defaultOpt, String phase, String friendly) {
		String s = (String)JOptionPane.showInputDialog(
                null,
                friendly,
                "Setup: " + subFlow + ", " + phase,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                defaultOpt);
		return s;
	}
}
