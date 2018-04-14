package com.oim.fx.view;

import com.oim.core.business.view.CreateGroupView;
import com.oim.ui.fx.classics.GroupEditFrame;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;

import javafx.application.Platform;

/**
 * @author XiaHui
 * @date 2017年6月3日 上午10:17:58
 */
public class CreateGroupViewImpl extends AbstractView implements CreateGroupView {

	GroupEditFrame frame;
	
	public CreateGroupViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new GroupEditFrame();
				frame.setTitleText("新建群");
				initEvent();
			}
		});
	}

	protected void initEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					frame.show();
					frame.toFront();
					frame.clearData();
				} else {
					frame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showPrompt(String text) {
		// TODO Auto-generated method stub

	}

}
