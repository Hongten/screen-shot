package com.b510.hongten.screenshot.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import com.b510.hongten.screenshot.common.Common;
import com.b510.hongten.screenshot.tray.ScreenShotTray;
import com.b510.hongten.screenshot.util.CircleEffectUtil;
import com.b510.hongten.screenshot.util.ScreenShotUtil;
import com.b510.hongten.screenshot.util.TearEffectUtil;

/**
 * @author MaiLingFeng
 * @author Hongten
 * @created 24 Feb, 2015
 */
public class ScreenShotUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 8030834318110376044L;
	static Logger log = Logger.getLogger(ScreenShotUI.class);

	JButton screenShotJButton;
	BufferedImage screenShotBufferedImage;
	CaptureView view;
	ScreenShotTray screenShotTray;
	public boolean iconed = false;

	public static boolean isTearEffect = false;
	public static boolean isCircleEffect = false;

	public ScreenShotUI(String title) {
		super(title);
		log.debug("Title : " + title);
	}

	public void init() {
		setContentPane(new ImagePanel(Common.SCREEN_SHOT_BACKGROUND));
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		screenShotJButton = new JButton(Common.SHOT);
		screenShotJButton.addActionListener(this);
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		jp.setOpaque(false);
		jp.add(new ImageLabel(Common.BLACK_CAT));
		jp.add(screenShotJButton);
		jp.add(new ImageLabel(Common.WHITE_CAT));
		JTextArea jta = new JTextArea();
		jta.setOpaque(false);
		jta.setEditable(false);
		jta.setForeground(Color.WHITE);
		jta.setFont(new Font(Common.REGULAR_SCRIPT, Font.PLAIN, 11));
		jta.setText(Common.OPERATION_GUIDE);
		con.add(jp, BorderLayout.CENTER);
		con.add(jta, BorderLayout.SOUTH);
		screenShotTray = new ScreenShotTray(ScreenShotUI.this);

		addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				iconed = true;
				setVisible(false);
			}

			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(ScreenShotUI.this,
						Common.WHETHER_MINIMIZE_PROMPT, Common.PROMPT,
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					iconed = true;
					setVisible(false);
				} else {
				}
				System.exit(0);
			}
		});
		pack();
		setSize(350, 230);
		setLocation(500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public void captureDesktop() throws Exception {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(d);
		screenShotBufferedImage = new BufferedImage((int) d.getWidth(),
				(int) d.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		Robot robot = new Robot(device);
		screenShotBufferedImage = robot.createScreenCapture(rect);
	}

	public void capture() {
		this.setVisible(false);
		try {
			Thread.sleep(200);
			captureDesktop();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (view == null) {
			view = new CaptureView(this, screenShotBufferedImage);
		} else {
			view.refreshBackGround(screenShotBufferedImage);
		}
	}

	public void saveCapture(int x1, int y1, int x2, int y2) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter(Common.POSTFIX_PNG,
				Common.IMAGE_TYPE_PNG));
		chooser.setSelectedFile(new File(chooser.getCurrentDirectory(),
				Common.SCREEN_SHOT.replace(Common.BLANK, Common.UNDERLINE)
						+ ScreenShotUtil.getDate(Common.DATE_FORMAT_YMDS)));
		int state = chooser.showSaveDialog(this);
		if (state == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			String descrition = filter.getDescription();
			String endWith = descrition.substring(2);
			int width = Math.abs(x2 - x1);
			int height = Math.abs(y2 - y1);
			if (isCircleEffect) {
				if (width > 0 && height > 0) {
					if (width > height) {
						CircleEffectUtil.C_R = height / 2;
						CircleEffectUtil.C_X = width / 2;
						CircleEffectUtil.C_Y = height / 2;
					} else {
						CircleEffectUtil.C_R = width / 2;
						CircleEffectUtil.C_X = width / 2;
						CircleEffectUtil.C_Y = height / 2;
					}
				}
			}

			captureToFile(
					screenShotBufferedImage.getSubimage(x1, y1,
							Math.abs(x2 - x1), Math.abs(y2 - y1)), endWith,
					new File(file.getAbsoluteFile() + Common.DOT + endWith));
		}
	}

	public void captureToFile(BufferedImage img, String endWith, File file) {
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			// Tear Effect
			if (isTearEffect) {
				img = TearEffectUtil.tearEffect(img);
			} else if (isCircleEffect) {
				// Circle Effect
				img = CircleEffectUtil.circleEffect(img);
			}
			ImageIO.write(img, endWith, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(screenShotJButton)) {
			capture();
		}
	}
}
