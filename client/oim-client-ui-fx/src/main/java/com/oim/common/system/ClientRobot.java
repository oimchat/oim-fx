package com.oim.common.system;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ClientRobot {

	private static Robot r;

	static {
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public ClientRobot() {
	}

	public static void mouseMove(int x, int y) {
		r.mouseMove(x, y);
	}
	

	public static void mousePress(int button) {
		if (button == 1) {
			r.mousePress(InputEvent.BUTTON1_MASK);
			return;
		}
		if (button == 2) {
			r.mousePress(InputEvent.BUTTON2_MASK);
			return;
		}
		if (button == 3) {
			r.mousePress(InputEvent.BUTTON3_MASK);
			return;
		}
	}

	public static void mouseRelease(int button) {
		if (button == 1) {
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			return;
		}
		if (button == 2) {
			r.mouseRelease(InputEvent.BUTTON2_MASK);
			return;
		}
		if (button == 3) {
			r.mouseRelease(InputEvent.BUTTON3_MASK);
			return;
		}
	}

	public static void mouseWheel(int rotation) {
		r.mouseWheel(rotation);
	}

	public static void keyPress(int keycode) {
		r.keyPress(keycode);
	}

	public static void keyRelease(int keycode) {
		r.keyRelease(keycode);
	}

	protected static final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	protected static final Rectangle rectangle = new Rectangle(d);// 全屏区域

	public static BufferedImage getScreen(int x, int y, int w, int h) {
		return r.createScreenCapture(new Rectangle(x, y, (-1 == w ? (int) d.getWidth() : w), (-1 == h ? (int) d.getHeight() : h)));
	}

	public static BufferedImage getScreen() {
		return r.createScreenCapture(rectangle);
	}

}
