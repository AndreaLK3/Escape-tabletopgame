package it.escape.client.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class LabelFrame extends JFrame
{
   	private static final long serialVersionUID = 1L;
   	
   	JPanel contentPane;
   	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private Icon map;
   	
   	public LabelFrame(String string) {
   		super(string);
   		setLayout(new FlowLayout());
   		
   		initializeLabels();
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		setSize(800, 600);
		setVisible(true);
   	}
   	
   	
	private void initializeLabels() {
	
		
	label1 = new JLabel("Escape from the Aliens in Outer Space");
	label1.setPreferredSize(new Dimension (200, 50));
	label1.setBackground(new Color(248, 213, 131));
	label1.setHorizontalAlignment(SwingConstants.LEFT);
    label1.setVerticalAlignment(SwingConstants.TOP);
	
	label2 = new JLabel("Players");
	label2.setPreferredSize(new Dimension (200, 50));
	label2.setBackground(new Color(200, 100, 200));
    label2.setHorizontalAlignment(SwingConstants.RIGHT);
    label2.setVerticalAlignment(SwingConstants.BOTTOM);
	
	label3 = new JLabel();
	Icon map = new ImageIcon(ImageScaler.resizeImage("resources/galilei.jpg", 400, 400));
	label3.setIcon(map);
	//label3.setPreferredSize(new Dimension (300, 300));
	 //label3.setBackground(new Color(200, 100, 200));
    
   
    label1.setOpaque(true);
    label2.setOpaque(true);
    label3.setOpaque(true);
  
    add(label1);
	add(label2);
	add(label3);

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
	
