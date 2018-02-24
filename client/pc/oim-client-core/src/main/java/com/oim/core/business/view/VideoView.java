package com.oim.core.business.view;

import com.onlyxiahui.app.base.view.View;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.AddressData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public interface VideoView  extends View {
	
	public void getAgree(String userId, AddressData videoAddress);
	
	public void getShut(String userId);
	
	public void showGetVideoFrame(UserData userData) ;
	
	public void showSendVideoFrame(UserData userData);

}
