package com.oim.test.function.remote;

import javax.swing.UIManager;

import com.oim.core.common.component.remote.EventData;
import com.oim.core.common.component.remote.EventDataHandler;
import com.oim.swing.function.ui.remote.RemoteControlFrame;
import com.oim.swing.function.ui.remote.server.OperationCapture;

public class RemoteControlFrameTest {
	RemoteControlFrame rcf = new RemoteControlFrame();

	public RemoteControlFrameTest() {
		rcf.setVisible(true);
		OperationCapture oc = new OperationCapture(new EventDataHandler() {

			@Override
			public void handle(EventData eventData) {
				System.out.println(eventData.getId());

			}
		});
		rcf.setKeyListener(oc);
		rcf.setMouseListener(oc);
		rcf.setMouseMotionListener(oc);
		rcf.setMouseWheelListener(oc);
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
				new RemoteControlFrameTest();
			}
		});
	}
}
