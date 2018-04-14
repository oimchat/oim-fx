package com.oim.fx.view;

import com.oim.core.business.view.NetSettingView;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.config.data.ServerConfig;
import com.oim.ui.fx.classics.NetSettingFrame;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;

import javafx.application.Platform;

/**
 * @author XiaHui
 * @date 2017-11-24 21:46:45
 */
public class NetSettingViewImpl extends AbstractView implements NetSettingView {
	NetSettingFrame frame;

	public NetSettingViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new NetSettingFrame();
				initEvent();
			}
		});
	}

	private void initEvent() {
		frame.setDoneAction(a -> {
			frame.hide();
			String address = frame.getAddress();
			ServerConfig sc = (ServerConfig) ConfigManage.get(ServerConfig.path, ServerConfig.class);
			sc.setAddress(address);
			ConfigManage.addOrUpdate(ServerConfig.path, sc);
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					if (!frame.isShowing()) {
						initConfig();
					}
					frame.show();
					frame.toFront();
				} else {
					frame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return (null != frame) && frame.isShowing();
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initConfig() {
		ServerConfig sc = (ServerConfig) ConfigManage.get(ServerConfig.path, ServerConfig.class);
		frame.setAddress(sc.getAddress());
	}
}
