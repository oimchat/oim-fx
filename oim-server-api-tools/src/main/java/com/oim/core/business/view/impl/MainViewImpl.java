package com.oim.core.business.view.impl;

import com.oim.core.business.back.DataBackActionImpl;
import com.oim.core.business.module.NetModule;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.sender.MessageSender;
import com.oim.core.business.view.MainView;
import com.oim.server.api.tools.ui.MainStage;
import com.only.net.action.ConnectBackAction;
import com.only.net.connect.ConnectData;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author: XiaHui
 * @date: 2018-02-23 10:42:25
 */
public class MainViewImpl extends AbstractView implements MainView {

	MainStage mainStage = new MainStage();

	public MainViewImpl(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
		mainStage.getOnlyTitlePane().addOnCloseAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SystemModule sm = appContext.getModule(SystemModule.class);
				sm.exit();
			}
		});
		mainStage.setOnConnect(a -> {
			connect();
		});
		mainStage.setOnSend(a -> {
			send();
		});
		mainStage.setOnExit(a -> {
			SystemModule sm = appContext.getModule(SystemModule.class);
			sm.exit();
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					mainStage.show();
					mainStage.toFront();
				} else {
					mainStage.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return mainStage.isShowing();
	}

	@Override
	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mainStage.showPrompt(text);
			}
		});
	}

	@Override
	public void setEditable(boolean editable) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mainStage.setConnectButtonDisable(editable);
			}
		});
	}

	@Override
	public void back(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mainStage.setBackText(message);
			}
		});
	}

	@Override
	public void receive(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mainStage.appendReceiveText(message);
			}
		});
	}

	private void connect() {
		if (mainStage.addressVerify()) {
			ConnectData connectData = new ConnectData();
			connectData.setAddress(mainStage.getAddress());
			connectData.setPort(mainStage.getPort());

			ConnectBackAction cba = new ConnectBackAction() {

				@Override
				public void connectBack(boolean success) {
					if (!success) {
						showPrompt("连接失败，请检查网络是否正常。");
					}else{
						MainViewImpl.this.back("连接成功。");
					}
				}
			};
			NetModule nm = appContext.getModule(NetModule.class);
			if (!nm.getConnectThread().isConnected()) {
				// 因为负责连接服务器的和负责发送消息的线程不同，在执行登录之前是没有连接的，所以在这里先添加个连接后回掉的action
				// 当连接成功后再把登陆消息发出去，不然先把消息发了，再连接就没有执行登陆操作了
				nm.getConnectThread().addConnectBackAction(cba);
				nm.getConnectThread().setConnectData(connectData);
				nm.getConnectThread().setAutoConnect(true);
			}
		}
	}

	private void send() {
		String message = mainStage.getRequestText();

		MessageSender ms = appContext.getSender(MessageSender.class);

		if (null != message && !message.isEmpty()) {
			DataBackActionImpl a = new DataBackActionImpl() {

				@Override
				public void back(String message) {
					MainViewImpl.this.back(message);
				}

				@Override
				public void lost() {
					MainViewImpl.this.back("发送失败！");
				}

				@Override
				public void timeOut() {
					MainViewImpl.this.back("发送超时！");
				}
			};
			ms.sendMessage(message, a);
		}
	}
}
