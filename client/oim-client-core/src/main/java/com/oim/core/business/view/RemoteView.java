package com.oim.core.business.view;

import com.oim.core.common.action.CallBackAction;
import com.oim.core.common.component.remote.EventDataAction;
import com.onlyxiahui.app.base.view.View;
import com.onlyxiahui.im.bean.UserData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public interface RemoteView  extends View {
	
	public void setScreenBytes(byte[] bytes);
	
	public void addClose(CallBackAction<String> callBackAction);
	
	public void addEventDataAction(EventDataAction eventDataAction);
	
	public void setUserData(UserData userData) ;
	
	public void showShutPrompt(String text);

}
