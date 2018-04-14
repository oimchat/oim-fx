package com.oim.core.common.app.video;

import com.oim.core.net.thread.SocketThread;
import com.onlyxiahui.app.base.AppContext;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月15日 下午7:48:47
 * @version 0.0.1
 */
public class VideoComponent {
	AppContext appContext;
	private SocketThread videoSocketThread = new SocketThread();

	public VideoComponent(AppContext appContext) {
		this.appContext = appContext;
		init();
	}

	public void init(){
		
	}
	public void start() {
		videoSocketThread.start();
	}
}
