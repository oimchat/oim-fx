package com.oim.core.business.module;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.common.action.CallAction;
import com.oim.core.common.action.GetAction;
import com.oim.core.common.component.VideoModule;
import com.oim.core.common.component.VoiceModule;
import com.oim.core.net.thread.SocketDataHandler;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2017年9月20日 下午5:49:06
 */
public class SystemModule extends AbstractComponent {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean login = false;// 用来标识是否已经成功登录了
	private boolean viewReady = false;
	private VideoModule videoModule = new VideoModule();
	private VoiceModule voiceModule=new VoiceModule();
	CallAction exitAction;
	
	public SystemModule(AppContext appContext) {
		super(appContext);
		long time = System.currentTimeMillis();
		initApp();
		if (logger.isDebugEnabled()) {
			logger.debug("initApp use time " + (System.currentTimeMillis() - time));
		}
		time = System.currentTimeMillis();
		initAction();
		if (logger.isDebugEnabled()) {
			logger.debug("initAction use time " + (System.currentTimeMillis() - time));
		}
	}

	/**
	 * 初始化各个模块
	 */
	private void initApp() {
		videoModule.setGetAction(new GetAction<String>() {

			@Override
			public String get() {
				PersonalBox pb=appContext.getBox(PersonalBox.class);
				return pb.getOwnerUserId();
			}
		});
		
		voiceModule.setGetAction(new GetAction<String>() {

			@Override
			public String get() {
				PersonalBox pb=appContext.getBox(PersonalBox.class);
				return pb.getOwnerUserId();
			}
		});
	}

	/**
	 * 初始化各个模块的
	 */
	private void initAction() {
	}

	/**
	 * 启动各个线程
	 */
	public void start() {
		videoModule.start();
		//voiceModule.start();
	}

	public void exit() {
		NetModule nm=appContext.getModule(NetModule.class);
		nm.closeConnect();
		if (null != exitAction) {
			exitAction.execute();
		} else {
			System.exit(0);
		}
	}

	public void setExitAction(CallAction exitAction) {
		this.exitAction = exitAction;
	}
	
	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isViewReady() {
		return viewReady;
	}

	public void setViewReady(boolean viewReady) {
		this.viewReady = viewReady;
	}
	
	public void addVideoHandler(SocketDataHandler socketDataHandler) {
		videoModule.addHandler(socketDataHandler);
	}

	public VideoModule getVideoModule() {
		return videoModule;
	}
	
	public VoiceModule getVoiceModule() {
		return voiceModule;
	}
}
