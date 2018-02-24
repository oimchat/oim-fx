package com.oim.test.function.remote;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.oim.core.common.component.RemoteModule;
import com.oim.core.common.component.remote.EventData;
import com.oim.core.common.component.remote.EventDataHandler;
import com.oim.core.common.component.remote.ScreenHandler;
import com.oim.swing.function.ui.remote.RemoteControlFrame;
import com.oim.swing.function.ui.remote.server.OperationCapture;
import com.only.net.connect.ConnectData;

public class RemoteServerTest {

	RemoteModule rm = new RemoteModule();
	RemoteControlFrame rcf = new RemoteControlFrame();

	public RemoteServerTest() {
		OperationCapture oc = new OperationCapture(new EventDataHandler() {

			@Override
			public void handle(EventData eventData) {
				rm.sendEvent("10001", eventData);
			}
		});

		rm.addHandler(new ScreenHandler() {

			@Override
			public void handle(String userId, byte[] bytes) {

				ByteArrayInputStream in = new ByteArrayInputStream(bytes);
				Image image;
				try {

					image = ImageIO.read(in);
					// image = image.getScaledInstance(480, 320,
					// Image.SCALE_SMOOTH);
					if (null != image) {
						ImageIcon icon = new ImageIcon(image);
						rcf.setIcon(icon);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
		rcf.setKeyListener(oc);
		rcf.setMouseListener(oc);
		rcf.setMouseMotionListener(oc);
		rcf.setMouseWheelListener(oc);

		rm.setUserId("10000");
		ConnectData cd = new ConnectData();
		cd.setAddress("192.168.1.200");
		cd.setPort(13010);
		rm.setConnectData(cd);
		rm.addClientId("10001");
		rcf.setVisible(true);
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new RemoteServerTest();
			}
		});
	}
}
