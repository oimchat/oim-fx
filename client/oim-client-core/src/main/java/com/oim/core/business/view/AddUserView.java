package com.oim.core.business.view;

import java.util.List;

import com.onlyxiahui.app.base.view.View;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserData;


/**
 * 描述： 添加好友或者加入群显示窗口
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public interface AddUserView extends View{


	public boolean isShowing() ;

	public void setUserData(UserData userData) ;
	
	public void showWaiting(boolean show) ;

	public void set(UserData userData, List<UserCategory> userCategoryList);
}
