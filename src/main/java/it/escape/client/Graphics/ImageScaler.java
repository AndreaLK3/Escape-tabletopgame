package it.escape.client.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageScaler {

	
	public static Image resizeImage(String imageLocation, int width, int height) {
		BufferedImage intermediate = readPictureAsBufferedImage(imageLocation);
		Image image2 = intermediate.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return image2;
	}
	
	
	private static BufferedImage readPictureAsBufferedImage (String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource(filename));
		} catch (IOException e) {
			e.getMessage();
			return null;
		}
		return image;
	}
}
