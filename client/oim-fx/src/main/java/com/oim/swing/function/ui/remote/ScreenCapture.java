package com.oim.swing.function.ui.remote;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ScreenCapture {

	protected static final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	protected static Robot r;
	
	static {
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getScreen(int x, int y, int w, int h) {
		return r.createScreenCapture(new Rectangle(x, y, (-1 == w ? (int) d.getWidth() : w), (-1 == h ? (int) d.getHeight() : h)));
	}
	
}
