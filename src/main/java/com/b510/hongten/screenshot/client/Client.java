/**
 * 
 */
package com.b510.hongten.screenshot.client;

import javax.swing.UIManager;

import com.b510.hongten.screenshot.common.Common;
import com.b510.hongten.screenshot.ui.ScreenShotUI;

/**
 * @author Hongten
 * @created 24 Feb, 2015
 */
public class Client {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ScreenShotUI screenShotUI = new ScreenShotUI(Common.SCREEN_SHOT);
			screenShotUI.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
