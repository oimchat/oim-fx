package com.oim.core.business.module;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oim.core.business.view.MainView;
import com.oim.core.net.connect.MessageHandler;
import com.oim.core.net.connect.SocketConnector;
import com.oim.core.net.server.Handler;
import com.oim.core.net.server.MessageReadHandler;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.net.NetServer;
import com.only.net.action.ConnectBackAction;
import com.only.net.action.ConnectStatusAction;
import com.only.net.connect.ConnectThread;
import com.only.net.data.action.DataBackAction;
import com.only.net.thread.DataReadThread;
import com.only.net.thread.DataWriteThread;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2017年9月21日 下午1:57:35
 */
public class NetModule extends AbstractComponent {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JsonParser jsonParser = new JsonParser();
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
		MessageReadHandler messageReadHandler = new MessageReadHandler(appContext);
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

				MainView mv = appContext.getSingleView(MainView.class);
				mv.setEditable(isConnected);
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
	public void write(String data) {
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
	public void write(String data, DataBackAction dataBackAction) {

		if (OnlyJsonUtil.mayBeJSON(data)) {
			String key = "00000000";

			JsonObject jo = jsonParser.parse(data).getAsJsonObject();

			if (jo.has("head")) {
				JsonObject hjo = jo.get("head").getAsJsonObject();
				key = (hjo.has("key")) ? hjo.get("key").getAsString() : null;
				if (null == key || key.isEmpty()) {
					key = this.getKey();
					hjo.addProperty("key", key);
					data = jo.toString();
				}
			}
			netServer.write(key, data, dataBackAction);
		} else {
			netServer.write(getKey(), data, dataBackAction);
		}
	}

	public String getKey() {
		return UUID.randomUUID().toString();
	}

	public void closeConnect() {
		netServer.closeConnect();
	}
}
