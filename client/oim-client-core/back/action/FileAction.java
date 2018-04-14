package com.oim.core.business.action;

import com.oim.core.business.service.FileService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.message.data.FileData;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@ActionMapping(value = "1.505")
public class FileAction extends AbstractAction {

	public FileAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.2.0002")
	public void receivedServerBack() {

	}

	@MethodMapping(value = "1.2.0006")
	public void receiveOfflineFile(
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("fileData") FileData fileData) {
		FileService fs=this.appContext.getService(FileService.class);
		fs.receiveOfflineFile(sendUserId, receiveUserId, fileData);
	}
}
