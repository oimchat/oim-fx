package com.oim.swing.function.ui.remote;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class RobotUtil {
	
	private static Robot r; 
	
	static {
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		} 
	}

	public static void mouseMove(int x, int y) {
		r.mouseMove(x, y);
	}

	public static void mousePress(int button) {
		if(button == 1) {
			r.mousePress(InputEvent.BUTTON1_MASK);
			return;
		}
		if(button == 2) {
			r.mousePress(InputEvent.BUTTON2_MASK);
			return;
		}
		if(button == 3) {
			r.mousePress(InputEvent.BUTTON3_MASK);
			return;
		}
	}

	public static void mouseRealse(int button) {
		if(button == 1) {
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			return;
		}
		if(button == 2) {
			r.mouseRelease(InputEvent.BUTTON2_MASK);
			return;
		}
		if(button == 3) {
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
	
}
