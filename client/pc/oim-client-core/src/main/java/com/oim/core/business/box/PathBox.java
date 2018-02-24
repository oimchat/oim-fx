package com.oim.core.business.box;

import com.oim.core.common.AppConstant;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.bean.UserData;

/**
 * @author XiaHui
 * @date 2017年6月18日 下午9:32:36
 */
public class PathBox extends AbstractBox {

	public PathBox(AppContext appContext) {
		super(appContext);
	}

	public String getScreenshotSavePath() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("screenshot");
		sb.append("/");
		return sb.toString();
	}

	public String getPersonalHeadPath() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("head/user");
		sb.append("/");
		return sb.toString();
	}

	public String getUserHeadPath() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("head/user");
		sb.append("/");
		return sb.toString();
	}

	public String getGroupHeadPath() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("head/group");
		sb.append("/");
		return sb.toString();
	}

	public String getUserChatImagePath() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("chat/user");
		sb.append("/");
		return sb.toString();
	}

	public String getGroupChatImagePath() {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		StringBuilder sb = new StringBuilder();
		sb.append(AppConstant.userHome);
		sb.append("/");
		sb.append(AppConstant.app_home_path);
		sb.append("/");
		sb.append(user.getNumber());
		sb.append("/");
		sb.append("images");
		sb.append("/");
		sb.append("chat/group");
		sb.append("/");
		return sb.toString();
	}
}
