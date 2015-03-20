package com.b510.hongten.screenshot.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.b510.hongten.screenshot.common.Common;
import com.b510.hongten.screenshot.ui.ScreenShotUI;

/**
 * @author MaiLingFeng
 * @author Hongten
 * @created 27 Feb, 2015
 */
public class ScreenShotTray implements ActionListener, MouseListener {
	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;

	private ScreenShotUI screenShotUI;
	private PopupMenu popupMenu = new PopupMenu();
	private MenuItem circleEffect = new MenuItem(Common.CIRCLE_EFFECT);
	private MenuItem tearEffect = new MenuItem(Common.TEAR_EFFECT);
	private MenuItem capture = new MenuItem(Common.CAPTURE);
	private MenuItem open = new MenuItem(Common.OPEN);
	private MenuItem exit = new MenuItem(Common.EXIT);

	public ScreenShotTray(ScreenShotUI frame) {
		this.screenShotUI = frame;
		icon = new ImageIcon(this.getClass().getClassLoader()
				.getResource(Common.TRAY_BLACK_CAT)).getImage();

		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();
			trayIcon = new TrayIcon(icon, Common.CLICK_TO_SCREEN_SHOT,
					popupMenu);
			popupMenu.add(circleEffect);
			popupMenu.add(tearEffect);
			popupMenu.add(capture);
			popupMenu.add(open);
			popupMenu.add(exit);

			try {
				systemTray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
				trayIcon.addMouseListener(this);
			}
		}
		trayIcon.addMouseListener(this);
		open.addActionListener(this);
		exit.addActionListener(this);
		capture.addActionListener(this);
		tearEffect.addActionListener(this);
		circleEffect.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == circleEffect) {
			ScreenShotUI.isCircleEffect = true;
			screenShotUI.capture();
		} else if (e.getSource() == tearEffect) {
			ScreenShotUI.isTearEffect = true;
			screenShotUI.capture();
		} else if (e.getSource() == open) {
			screenShotUI.iconed = false;
			screenShotUI.setVisible(true);
			screenShotUI.setExtendedState(JFrame.NORMAL);
		} else if (e.getSource() == capture) {
			screenShotUI.capture();
		} else {
			System.exit(0);
		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1 && e.getButton() != MouseEvent.BUTTON3) {
			screenShotUI.capture();
		}
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}
}
