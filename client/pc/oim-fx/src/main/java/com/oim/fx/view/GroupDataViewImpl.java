package com.oim.fx.view;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.view.GroupDataView;
import com.oim.core.common.util.HereStringUtil;
import com.oim.ui.fx.classics.InfoFrame;
import com.oim.ui.fx.classics.add.InfoPane;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class GroupDataViewImpl extends AbstractView implements GroupDataView {

	InfoFrame frame;// = new InfoFrame();

	public GroupDataViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new InfoFrame();
			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					frame.show();
					frame.toFront();
				} else {
					frame.hide();
				}
			}
		});
	}

	public void setGroup(Group group) {
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (null != group) {
					
					Image image =him.getGroupHead(group.getId());
					
					StringBuilder sb = new StringBuilder();
					sb.append("名称：");
					sb.append(HereStringUtil.value(group.getName()));
					sb.append("\n");
					sb.append("\n");
					
					sb.append("群号：");
					sb.append(HereStringUtil.value(group.getNumber() + ""));
					sb.append("\n");
					sb.append("\n");
					
					sb.append("简介：");
					sb.append(HereStringUtil.value(group.getIntroduce()));
					sb.append("\n");
					sb.append("\n");
					
					sb.append("公告：");
					sb.append(HereStringUtil.value(group.getPublicNotice()));
					sb.append("\n");
					sb.append("\n");
					
					InfoPane ip = frame.getInfoPane();
					ip.setHeadImage(image);
//					ip.setName("名称：" + HereStringUtil.value(group.getName()));
//					ip.setNumber("群号：" + HereStringUtil.value(group.getNumber() + ""));
					ip.setInfoText(sb.toString());
				} else {
					InfoPane ip = frame.getInfoPane();
					// ip.setHeadImage(image);
					ip.setName("");
					ip.setNumber("");
					ip.setInfoText("");
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return false;
	}
}
