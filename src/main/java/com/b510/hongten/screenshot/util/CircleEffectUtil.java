/**
 * 
 */
package com.b510.hongten.screenshot.util;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * @author Hongten
 * @created 20 Mar, 2015
 */
public class CircleEffectUtil {

	static Logger log = Logger.getLogger(CircleEffectUtil.class);
	// radius
	public static int C_R = 60;
	// Coordinate of circle center (C_X, C_Y)
	public static int C_X = 100;
	public static int C_Y = 100;

	public static BufferedImage circleEffect(BufferedImage bImage) {
		log.debug("BEGIN, Circle Effect....");
		ImageIcon imageIcon = new ImageIcon(bImage);
		BufferedImage bufferedImage = new BufferedImage(
				imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
		g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

		for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
			for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
				int rgb = bufferedImage.getRGB(x, y);
				if (isOnCircle(x, y)) {
					rgb = (0 << 24) | (rgb & 0x00ffffff);
					bufferedImage.setRGB(x, y, rgb);
				}
			}
		}
		g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
		log.debug("END, Circle Effect....");
		return bufferedImage;
	}

	private static boolean isOnCircle(int x, int y) {
		Point circleO = new Point(C_X, C_Y);
		Point pointA = new Point(x, y);
		double cirX = Math.abs(circleO.x - pointA.x);
		double cirY = Math.abs(circleO.y - pointA.y);
		double circleR = Math.sqrt(cirX * cirX + cirY * cirY);
		// Out of circle or on the circle return true
		if (circleR >= C_R) {
			return true;
		}
		return false;
	}
}
