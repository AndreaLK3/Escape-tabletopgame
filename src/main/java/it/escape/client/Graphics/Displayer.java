package it.escape.client.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class Displayer extends JFrame
{
   	private static final long serialVersionUID = 1L;
   	
   	private GridBagConstraints constraints;
   	//private int currentRow=0, currentColumn=0;
   	
   	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	JScrollPane mapScrollPane;
	private JTextArea playerArea;
	private JTextArea statusArea;
	private JTextArea lastNoiseArea;
	private Icon map;
   	
   	public Displayer(String string) {
   		super(string);
   		setLayout(new GridBagLayout());
   		constraints = new GridBagConstraints();
   		
   		initializeLabels();
   		initializePanels();
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(800, 600);
		setVisible(true);
   	}
   	
   	
	private void initializePanels() {
		JPanel panel1 = new JPanel();
		
		panel1.setLayout(new FlowLayout());
		
		playerArea = new JTextArea();
		playerArea.setEditable(false);
		playerArea.setText("Giocatore 1\n");
		playerArea.setVisible(true);
		panel1.add(playerArea);
		
		statusArea = new JTextArea();
		statusArea.setEditable(false);
		statusArea.setText("Alive\n");
		panel1.add(statusArea);
		
		lastNoiseArea = new JTextArea();
		lastNoiseArea.setEditable(false);
		lastNoiseArea.setText("Alive\n");
		panel1.add(lastNoiseArea);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(panel1,constraints);
	    resetConstraints(constraints);
	}


	private void initializeLabels() {
		
	label1 = new JLabel("Escape from the Aliens in Outer Space");
	label1.setBackground(new Color(248, 213, 131));
	label1.setHorizontalAlignment(SwingConstants.CENTER);
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.gridwidth = GridBagConstraints.REMAINDER;
    add(label1,constraints);
    resetConstraints(constraints);
	
	label2 = new JLabel("Players");
	label2.setBackground(new Color(200, 100, 200));
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.gridx = 0;
	constraints.gridy = 1;
	constraints.weightx = 0;
	constraints.weighty = 0;
	add(label2,constraints);
	resetConstraints(constraints);
	
	label3 = new JLabel("Status");
	label3.setBackground(new Color(200, 100, 20));
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.gridx = 1;
	constraints.gridy = 1;
	constraints.weightx = 0;
	constraints.weighty = 0;
	add(label3,constraints);
	resetConstraints(constraints);
	
	label4 = new JLabel("LastNoise");
	label4.setBackground(new Color(50, 100, 200));
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.gridx = 2;
	constraints.gridy = 1;
	constraints.weightx = 0;
	constraints.weighty = 0;
	add(label4,constraints);
	resetConstraints(constraints);
	
	label5 = new JLabel();
	Icon map = new ImageIcon(ImageScaler.resizeImage("resources/galilei.jpg", 1000, 1000));
	label5.setIcon(map);
	mapScrollPane = new JScrollPane(label5);
	mapScrollPane.setPreferredSize(new Dimension(400,400)); 
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridx = 3;
	constraints.gridy = 1;
	constraints.weightx = 1;
	constraints.weighty = 1;
	constraints.gridwidth = 1;
	constraints.gridheight = 2;
	constraints.anchor = GridBagConstraints.CENTER;
	add(mapScrollPane, constraints);
	resetConstraints(constraints);
	
	List<JLabel> labels = Arrays.asList(label1,label2,label3,label4,label5);
	
   for (JLabel l : labels)
	   l.setOpaque(true);
 
   

	}
   	
	/**Add a label  on a row... for now, unused (I could use Panels)
	 * @param label
	 * @param row
	 */
	private void addLabelOnRow(JLabel label, int row){
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = row;
		constraints.weightx = 0;
		constraints.weighty = 0;
		add(label2,constraints);
		resetConstraints(constraints);
	}
	
	private void resetConstraints(GridBagConstraints constraints) {
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridwidth = 1;	
		constraints.gridheight = 1;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.anchor = GridBagConstraints.CENTER;
	}
   	
   	
   	public static void main( String[] args )
    {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						Displayer playerFrame = new Displayer("Escape from the Aliens in Outer Space");
				
					}
				}
		);
		}

   	
   	
   
   	


	
}
	
