package com.oim.core.business.view;

import com.onlyxiahui.app.base.view.View;

/**
 * 描述： 添加好友或者加入群显示窗口
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午10:42:19
 * @version 0.0.1
 */
public interface NoticeView extends View {

	public void addTextNotice(String openType, String url, String title, String content);

}
