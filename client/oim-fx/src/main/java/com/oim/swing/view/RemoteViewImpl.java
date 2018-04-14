package com.oim.swing.view;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.oim.core.business.manager.RemoteManager;
import com.oim.core.business.view.RemoteView;
import com.oim.core.common.action.CallBackAction;
import com.oim.core.common.component.remote.EventData;
import com.oim.core.common.component.remote.EventDataAction;
import com.oim.core.common.component.remote.EventDataHandler;
import com.oim.swing.function.ui.remote.RemoteControlFrame;
import com.oim.swing.function.ui.remote.server.OperationCapture;
import com.only.OnlyMessageBox;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.UserData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class RemoteViewImpl extends AbstractView implements RemoteView {

	RemoteControlFrame remoteControlFrame = new RemoteControlFrame();
	CallBackAction<String> callBackAction;
	UserData userData;
	EventDataAction eventDataAction;

	public RemoteViewImpl(AppContext appContext) {
		super(appContext);
		initEvent();
		init();
	}

	public void setVisible(boolean visible) {
		remoteControlFrame.setVisible(visible);
	}

	@Override
	public boolean isShowing() {
		return remoteControlFrame.isShowing();
	}

	private void init() {
		OperationCapture oc = new OperationCapture(new EventDataHandler() {

			@Override
			public void handle(EventData eventData) {
				if (null != userData) {
					RemoteManager rm = appContext.getManager(RemoteManager.class);
					rm.getRemoteModule().sendEvent(userData.getId(), eventData);
				}
			}
		});

		remoteControlFrame.setKeyListener(oc);
		remoteControlFrame.setMouseListener(oc);
		remoteControlFrame.setMouseMotionListener(oc);
		remoteControlFrame.setMouseWheelListener(oc);
	}

	private void initEvent() {
		remoteControlFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}
		});

	}

	private void formWindowClosing(java.awt.event.WindowEvent evt) {
		int button = OnlyMessageBox.createQuestionMessageBox(remoteControlFrame, "关闭", "是否关闭远程控制？").open();
		if (OnlyMessageBox.YES_OPTION == button) {
			closing();
		}
	}

	private void closing() {
		if (null != callBackAction) {
			if (null != userData) {
				callBackAction.execute(userData.getId());
			}
		}
		remoteControlFrame.setVisible(false);
	}

	@Override
	public void setScreenBytes(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		Image image;
		try {
			image = ImageIO.read(in);
			if (null != image) {
				ImageIcon icon = new ImageIcon(image);
				remoteControlFrame.setIcon(icon);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addClose(CallBackAction<String> callBackAction) {
		this.callBackAction = callBackAction;
	}

	@Override
	public void setUserData(UserData userData) {
		this.userData = userData;
		if (null != userData) {
			remoteControlFrame.setTitle(userData.getAccount() + "的远程桌面");
		}
	}

	@Override
	public void showShutPrompt(String text) {
		OnlyMessageBox.createWarningMessageBox(remoteControlFrame, "失败", text).open();
		remoteControlFrame.setVisible(false);
	}

	@Override
	public void addEventDataAction(EventDataAction eventDataAction) {
		// TODO Auto-generated method stub

	}
}
