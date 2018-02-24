package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.PageData;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class NoticeSender extends BaseSender{

	public NoticeSender(AppContext appContext) {
		super(appContext);
	}
	
	public void getTextNoticeList(PageData page,DataBackAction dataBackAction) {
		
		Message message = new Message();
		message.put("page", page);
		
		Head head = new Head();
		head.setAction("1.700");
		head.setMethod("1.1.0003");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message, dataBackAction);
	}
}
