/**
 * 
 */
package com.b510.hongten.screenshot.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * @author Hongten
 * @created 20 Mar, 2015
 */
public class TearEffectUtil {

	static Logger log = Logger.getLogger(TearEffectUtil.class);

	// Effect Proportion
	private static double tornEdge = 0.02;

	public static BufferedImage tearEffect(BufferedImage bImage) {
		log.debug("BEGIN, Using Tear Effect Util...");
		ImageIcon imageIcon = new ImageIcon(bImage);
		BufferedImage bufferedImage = new BufferedImage(
				imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
		g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		log.debug("w,h = " + width + "," + height);

		int tornEdgeWidth = (int) (width * tornEdge);
		int tornEdgeHeight = (int) (height * tornEdge);

		log.debug("Tw,Th = " + tornEdgeWidth + "," + tornEdgeHeight);

		int randomY = tornEdgeHeight;
		int temp = randomY;
		for (int x = bufferedImage.getMinX(); x < width; x++) {
			if (randomY <= 0) {
				Random random = new Random();
				String dif = String.valueOf(tornEdgeHeight);
				randomY = random.nextInt(Integer.valueOf(dif));
			}
			temp = randomY;
			for (int y = (int) (height - temp); y < height; y = (int) (height - temp)) {
				int rgb = bufferedImage.getRGB(x, y);
				rgb = (0 << 24) | (rgb & 0x00ffffff);
				bufferedImage.setRGB(x, y, rgb);
				temp--;
			}
			randomY--;
		}

		int randomX = tornEdgeWidth;
		temp = randomX;
		for (int y = bufferedImage.getMinY(); y < height; y++) {
			if (randomX <= 0) {
				Random random = new Random();
				String dif = String.valueOf(tornEdgeWidth);
				randomX = random.nextInt(Integer.valueOf(dif));
			}
			temp = randomX;
			for (int x = (int) (width - temp); x < width; x = (int) (width - temp)) {
				int rgb = bufferedImage.getRGB(x, y);
				rgb = (0 << 24) | (rgb & 0x00ffffff);
				bufferedImage.setRGB(x, y, rgb);
				temp--;
			}
			randomX--;
		}

		g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
		log.debug("END, Using Tear Effect Util...");
		return bufferedImage;
	}
}
