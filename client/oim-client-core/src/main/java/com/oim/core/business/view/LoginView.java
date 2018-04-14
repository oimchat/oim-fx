package com.oim.core.business.view;

import java.util.List;

import com.oim.core.common.data.UserSaveData;
import com.onlyxiahui.app.base.view.View;

/**
 * 登录界面
 * 
 * @author: XiaHui
 * @date 2018-01-21 10:30:05
 */
public interface LoginView extends View {

	public boolean isSavePassword();

	public void showWaiting(boolean show);

	public void setUserSaveDataList(List<UserSaveData> list);

	public void setCurrentUserSaveData(UserSaveData userSaveData);
}
