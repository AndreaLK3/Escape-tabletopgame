package it.escape.client;

import it.escape.utils.FilesHelper;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**This class is useful to avoid code duplication 
 * between ClientInitializerGUIRMI and ClientInitializerGUISocket.
 * It contains the Progress Dialog that is displayed and disposed of by both, 
 * and some common variables.
 * @author andrea*/
public class ClientInitializerGUI {
	
	protected static JDialog pleaseWait;
	
	protected static ClientLocalSettings locals;
	
	protected static void openProgressDialog() {
		JOptionPane optionPane;
		try {
			optionPane = new JOptionPane(
					"Connecting to " + locals.getDestinationServerAddress() + ":" + locals.getServerPort(),
					JOptionPane.INFORMATION_MESSAGE, 
					JOptionPane.DEFAULT_OPTION,
					new ImageIcon(FilesHelper.getResourceAsByteArray("resources/artwork/launcher/connecting.gif")),
					new Object[]{},
					null);
			pleaseWait = new JDialog();
			pleaseWait.setTitle("Connecting...");
			pleaseWait.setLocationRelativeTo(null);
			pleaseWait.setContentPane(optionPane);
			pleaseWait.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			pleaseWait.pack();
			pleaseWait.setVisible(true);
		} catch (IOException e) {
			popupError("Cannot initialize dialog");
		}
	}
	
	protected static void popupError(String message) {
		JOptionPane.showMessageDialog(null,
			    message,
			    "Network error",
			    JOptionPane.WARNING_MESSAGE);
	}

}
