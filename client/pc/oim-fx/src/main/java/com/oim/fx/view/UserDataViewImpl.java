package com.oim.fx.view;

import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.view.UserDataView;
import com.oim.core.common.util.OimDateUtil;
import com.oim.core.common.util.HereStringUtil;
import com.oim.ui.fx.classics.InfoFrame;
import com.oim.ui.fx.classics.add.InfoPane;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public class UserDataViewImpl extends AbstractView implements UserDataView {

	InfoFrame frame;// = new InfoFrame();

	public UserDataViewImpl(AppContext appContext) {
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

	@Override
	public boolean isShowing() {
		return false;
	}

	@Override
	public void showUserData(UserData userData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (null != userData) {
					HeadImageManager him = appContext.getManager(HeadImageManager.class);
					Image image = him.getUserHead(userData.getId(), 100);
					
					//String nickname = userData.getNickname();
					//String account = userData.getAccount();
					String gender = userData.getGender();
					StringBuilder sb = new StringBuilder();
					
					sb.append("昵称：");
					sb.append(HereStringUtil.value(userData.getNickname()));
					sb.append("\n");
					sb.append("\n");
					
					sb.append("账号：");
					sb.append(HereStringUtil.value(userData.getAccount())+ "");
					sb.append("\n");
					sb.append("\n");
					
					String genderText = "保密";
					if ("1".equals(gender)) {
						genderText = "男";
					}
					if ("2".equals(gender)) {
						genderText = "女";
					}
					sb.append("性别：");
					sb.append(genderText);
					sb.append("\n");
					sb.append("\n");
					
					sb.append("年龄：");
					if (null != userData.getBirthdate()) {
						int y = OimDateUtil.beforePresentYearCount(userData.getBirthdate());
						sb.append(y);
						sb.append("岁");
					}
					sb.append("\n");
					sb.append("\n");
					
					sb.append("故乡：");
					sb.append(HereStringUtil.value(userData.getHomeAddress()));
					sb.append("\n");
					sb.append("\n");
					
					sb.append("所在地：");
					sb.append(HereStringUtil.value(userData.getLocationAddress()));
					sb.append("\n");
					sb.append("\n");
					
					sb.append("个性签名：");
					sb.append(HereStringUtil.value(userData.getSignature()));
					sb.append("\n");
					
					InfoPane ip = frame.getInfoPane();
					ip.setHeadImage(image);
//					ip.setName("昵称："+HereStringUtil.value(userData.getNickname()));
//					ip.setNumber("账号："+HereStringUtil.value(userData.getAccount())+ "");
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
}
