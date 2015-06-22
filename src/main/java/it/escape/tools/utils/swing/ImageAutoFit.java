package it.escape.tools.utils.swing;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Runnable that will monitor a Swing label for changes in
 * its size, and apply an image background accordingly
 * @author michele
 *
 */
public class ImageAutoFit implements Runnable {
	
	private static final int SCANTIME = 300;

	private JLabel label;
	
	private String image;
	
	private Icon icon;
	
	private int oldX;
	
	private int oldY;

	/**The constructor*/
	public ImageAutoFit(JLabel label, String image) {
		this.label = label;
		this.image = image;
		oldX = 0;
		oldY = 0;
		
	}
	
	/**Starts this object's thread, that after a predefined timespan calls the private method update()*/
	public void setUpAutoFittingImage() {
		new Thread(this).start();
	}
	
	/**If the new dimensions of the label are different from the memorized ones,
	 * invoke ImageScaler.resizeImage on the Icon and reapply it on the JLabel*/
	private void update() {
		if (label.getWidth() != oldX || label.getHeight() != oldY) {
			icon = new ImageIcon(ImageScaler.resizeImage(image, label.getWidth(), -1));
			label.setIcon(icon);
			label.setHorizontalAlignment(JLabel.CENTER);
			oldX = label.getWidth();
			oldY = label.getHeight();
		}
	}

	public void run() {
		while (label != null) {
			try {
				Thread.sleep(SCANTIME);
			} catch (InterruptedException e) {
			}
			update();
		}
	}
}
