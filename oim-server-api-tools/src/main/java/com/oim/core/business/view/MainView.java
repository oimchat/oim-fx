package com.oim.core.business.view;

import com.onlyxiahui.app.base.view.View;

/**
 * @author: XiaHui
 * @date: 2018-02-22 11:21:31
 */
public interface MainView extends View {

	public void setEditable(boolean editable);

	/**
	 * 请求服务器后返回消息
	 * 
	 * @author: XiaHui
	 * @param message
	 * @createDate: 2018-02-22 13:37:07
	 * @update: XiaHui
	 * @updateDate: 2018-02-22 13:37:07
	 */
	public void back(String message);

	/**
	 * 收到服务器推送消息
	 * 
	 * @author: XiaHui
	 * @param message
	 * @createDate: 2018-02-22 13:37:28
	 * @update: XiaHui
	 * @updateDate: 2018-02-22 13:37:28
	 */
	public void receive(String message);
}
