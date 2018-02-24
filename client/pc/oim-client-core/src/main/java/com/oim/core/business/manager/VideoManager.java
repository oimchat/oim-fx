package com.oim.core.business.manager;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.view.VideoView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.AddressData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月15日 下午7:52:44
 * @version 0.0.1
 */
public class VideoManager extends AbstractManager {
	
	private AddressData videoServerAddress;

	private long time=0;
	
	public VideoManager(AppContext appContext) {
		super(appContext);
		init();
	}

	public void receivedServerBack(){
		time=System.currentTimeMillis();
	}
	
	private void init() {
		Timer timer = new Timer();
		timer.schedule(new UpdateVideoTask(), 1000, 20000);
	}



	public void getAgree(String userId, AddressData videoAddress) {
		VideoView videoView=this.appContext.getSingleView(VideoView.class);
		videoView.getAgree(userId, videoAddress);
	}

	public void getShut(String userId) {
		VideoView videoView=this.appContext.getSingleView(VideoView.class);
		videoView.getShut(userId);
	}


	public void setVideoServerAddress(AddressData videoServerAddress) {
		this.videoServerAddress = videoServerAddress;
		ExecuteTask et = new ExecuteTask() {

			@Override
			public void execute() {
				for (int i = 0; i < 5; i++) {
					handlerVideoServerAddress();
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					long tempTime=(System.currentTimeMillis()-time);
					if(tempTime>(1000*60*10)){
						break;
					}
				}
			}
		};
		this.appContext.add(et);
	}

	private void handlerVideoServerAddress() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		String address = (null == videoServerAddress) ? null : videoServerAddress.getAddress();
		int port = (null == videoServerAddress) ? -1 : videoServerAddress.getPort();
		String userId = (null == user) ? null : user.getId();
		if (null != address && !"".endsWith(address) && -1 != port && null != userId && !"".equals(userId)) {
			SocketAddress socketAddress = new InetSocketAddress(address, port);
			String key=userId+","+"1";
			SystemModule sm = appContext.getModule(SystemModule.class);
			sm.getVideoModule().connectServer(key.getBytes(), socketAddress);
		}
	}

	class UpdateVideoTask extends TimerTask {

		@Override
		public void run() {
			long tempTime=(System.currentTimeMillis()-time);
			if(tempTime>(1000*60*10)){
				handlerVideoServerAddress();
			}
		}
	}

	

	public void showSendVideoFrame(UserData userData) {
		VideoView videoView=this.appContext.getSingleView(VideoView.class);
		videoView.showSendVideoFrame(userData);
	}

	public void showGetVideoFrame(UserData userData) {
		VideoView videoView=this.appContext.getSingleView(VideoView.class);
		videoView.showGetVideoFrame(userData);
	}
}
