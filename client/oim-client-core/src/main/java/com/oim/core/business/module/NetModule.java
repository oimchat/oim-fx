package com.oim.core.business.module;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.controller.PersonalController;
import com.oim.core.business.view.MainView;
import com.oim.core.net.connect.MessageHandler;
import com.oim.core.net.connect.SocketConnector;
import com.oim.core.net.server.Handler;
import com.oim.core.net.server.MessageReadHandler;
import com.only.net.NetServer;
import com.only.net.action.ConnectBackAction;
import com.only.net.action.ConnectStatusAction;
import com.only.net.connect.ConnectThread;
import com.only.net.data.action.DataBackAction;
import com.only.net.thread.DataReadThread;
import com.only.net.thread.DataWriteThread;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.Data;
import com.onlyxiahui.im.message.data.LoginData;

/**
 * @author XiaHui
 * @date 2017年9月21日 下午1:57:35
 */
public class NetModule extends AbstractComponent {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Handler handler;// 负责处理TCP接受到的消息
	private SocketConnector connector;// 连接实体
	private NetServer netServer;

	public NetModule(AppContext appContext) {
		super(appContext);
		initApp();
		initAction();
	}

	/**
	 * 初始化各个模块
	 */
	private void initApp() {

		handler = new Handler();
		connector = new SocketConnector(handler);
		netServer = new NetServer(connector);
		netServer.getDataWriteThread().setSleepTime(1);
		netServer.getDataReadThread().setSleepTime(1);
		
		handler.addMessageHandler(new MessageHandler() {

			@Override
			public void setBackTime(long backTime) {
				netServer.getDataWriteThread().setBackTime(backTime);
			}

			@Override
			public void back(Object data) {
				netServer.getDataReadThread().back(data);
			}

			@Override
			public void addExceptionData(String key) {
				netServer.getDataReadThread().addExceptionData(key);
			}
		});

		ActionFactory actionFactory = appContext.getModule(ActionFactory.class);

		MessageReadHandler messageReadHandler = new MessageReadHandler();
		messageReadHandler.setActionMap(actionFactory);
		netServer.getDataReadThread().addReadHandler(messageReadHandler);
	}

	/**
	 * 初始化各个模块的
	 */
	private void initAction() {

		netServer.addConnectBackAction(new ConnectBackAction() {

			@Override
			public void connectBack(boolean success) {
				if (logger.isDebugEnabled()) {
					String message = success ? "连接成功。" : "连接失败！";
					logger.debug(message);
				}
			}
		});
		/**
		 * * 为处理连接的线程添加连接状态变化的action，当连接断开了或者连接成功触发 **
		 */
		netServer.addConnectStatusAction(new ConnectStatusAction() {

			@Override
			public void connectStatusChange(boolean isConnected) {
				SystemModule sm = appContext.getModule(SystemModule.class);
				boolean login = sm.isLogin();
				if (isConnected && login) {// 如果是已经登录了，然后断网了，恢复网络后再次成功连接，那么进行自动重连接
					PersonalController pc = appContext.getController(PersonalController.class);
					pc.autoReconnect();
				}
				if (login) {
					PersonalBox pb = appContext.getBox(PersonalBox.class);
					LoginData loginData = pb.getLoginData();
					String status = isConnected ? loginData.getStatus() : UserData.status_offline;
					MainView mainView = appContext.getSingleView(MainView.class);
					mainView.setStatus(status);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("状态：" + isConnected);
				}
			}
		});
	}

	/**
	 * 启动各个线程
	 */
	public void start() {
		netServer.start();
	}

	public ConnectThread getConnectThread() {
		return netServer.getConnectThread();
	}

	public DataReadThread getDataReadThread() {
		return netServer.getDataReadThread();
	}

	public DataWriteThread getDataWriteThread() {
		return netServer.getDataWriteThread();
	}

	/**
	 * 发送信息
	 *
	 * @param data
	 *            :信息
	 */
	public void write(Data data) {
		write(data, null);
	}

	/**
	 * 发送信息
	 *
	 * @param data
	 *            :信息
	 * @param dataBackAction
	 *            :回调Action
	 */
	public void write(Data data, DataBackAction dataBackAction) {
		if (data.getHead() != null) {
			if (data.getHead().getKey() == null) {
				data.getHead().setKey(getKey());
			}
			String key = data.getHead().getKey();
			netServer.write(key, data, dataBackAction);
		}
	}

	public String getKey() {
		return UUID.randomUUID().toString();
	}

	public void closeConnect() {
		netServer.closeConnect();
	}
}
