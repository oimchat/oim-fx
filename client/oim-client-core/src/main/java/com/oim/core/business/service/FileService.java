package com.oim.core.business.service;

import com.oim.core.business.view.FileDownloadView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.message.data.FileData;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年3月31日 上午11:45:15 version 0.0.1
 */
public class FileService extends AbstractService {

	public FileService(AppContext appContext) {
		super(appContext);
	}

	public void receiveOfflineFile(String sendUserId, String receiveUserId, FileData fileData) {
		FileDownloadView fuv = this.appContext.getSingleView(FileDownloadView.class);
		fuv.fileDownload(fileData);
	}
}
