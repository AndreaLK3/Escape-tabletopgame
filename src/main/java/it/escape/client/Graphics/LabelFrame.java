package it.escape.client.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class LabelFrame extends JFrame
{
   	private static final long serialVersionUID = 1L;
   	
   	private GridBagConstraints constraints;
   	//private int currentRow=0, currentColumn=0;
   	
   	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	JScrollPane mapScrollPane;
	private JTextArea area1;
	private Icon map;
   	
   	public LabelFrame(String string) {
   		super(string);
   		setLayout(new GridBagLayout());
   		constraints = new GridBagConstraints();
   		
   		initializeLabels();
   		//initializeTextAreas();
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(800, 600);
		setVisible(true);
   	}
   	
   	
	private void initializeTextAreas() {
		area1 = new JTextArea("Giocatore1\n");
		area1.setEditable(false);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(area1, constraints);
	}


	private void initializeLabels() {
	
		
	label1 = new JLabel("Escape from the Aliens in Outer Space");
	label1.setBackground(new Color(248, 213, 131));
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridx = 0;
	constraints.gridy = 0;
	
    add(label1,constraints);
	
	label2 = new JLabel("Players");
	label2.setBackground(new Color(200, 100, 200));
	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.gridx = 0;
	constraints.gridy = 1;
	add(label2,constraints);

	
	label3 = new JLabel();
	Icon map = new ImageIcon(ImageScaler.resizeImage("resources/galilei.jpg", 1000, 1000));
	label3.setIcon(map);
	mapScrollPane = new JScrollPane(label3);
	mapScrollPane.setPreferredSize(new Dimension(400,400));
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridx = 1;
	constraints.gridy = 1;
	constraints.weightx = 1;	
	constraints.anchor = GridBagConstraints.CENTER;
	add(mapScrollPane, constraints);
    
   
    label1.setOpaque(true);
    label2.setOpaque(true);
    label3.setOpaque(true);
 
   

	}
   	
	
   	
   	
   	public static void main( String[] args )
    {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						LabelFrame playerFrame = new LabelFrame("Escape from the Aliens in Outer Space");
				
					}
				}
		);
		}

   	
   	
   
   	


	
}
	
