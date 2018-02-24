package com.oim.test.function.remote;

import javax.swing.UIManager;

import com.oim.core.common.component.RemoteModule;
import com.only.net.connect.ConnectData;

/**
 * @author: XiaHui
 * @date: 2017年4月12日 下午2:16:34
 */
public class RemoteClientTest {
	RemoteModule rm = new RemoteModule();

	public RemoteClientTest() {
		ConnectData cd = new ConnectData();
		cd.setAddress("192.168.1.200");
		cd.setPort(13010);
		
		rm.setConnectData(cd);
		rm.setUserId("10001");
		rm.setServerId("10000");
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
				new RemoteClientTest();
			}
		});
	}
}