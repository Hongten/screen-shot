package com.b510.hongten.screenshot.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author MaiLingFeng
 * @author Hongten
 * @created 20 Mar, 2015
 */
public class ImageLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public ImageLabel(String imgURL) {
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader()
				.getResource(imgURL));
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
	}

	public ImageLabel(ImageIcon icon) {
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
	}

}
