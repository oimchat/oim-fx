package com.oim.test.function.remote;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.oim.swing.function.ui.remote.RemoteControlFrame;
import com.oim.swing.function.ui.remote.ScreenCapture;

public class RCClient {

	public static void main(String[] args) throws IOException {

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		RemoteControlFrame rcm = new RemoteControlFrame();
		rcm.setVisible(true);
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					BufferedImage img = ScreenCapture.getScreen(0, 0, (int) d.getWidth(), (int) d.getHeight());
					rcm.setIcon(new ImageIcon(img));
					try {
						Thread.sleep(20);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
