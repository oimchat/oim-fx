package com.oim.core.business.controller;

import java.io.File;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.sender.FileSender;
import com.oim.core.business.view.FileUploadView;
import com.oim.core.common.action.CallBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractController;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.FileData;

public class ChatController extends AbstractController {

	public ChatController(AppContext appContext) {
		super(appContext);
	}

	public void sendFile(final String receiveUserId, File file) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		final String sendUserId = (null == user) ? null : user.getId();
		CallBackAction<FileData> callBackAction = new CallBackAction<FileData>() {

			@Override
			public void execute(FileData fileData) {
				FileSender fs = appContext.getSender(FileSender.class);
				fs.sendOfflineFile(receiveUserId, sendUserId, fileData);
			}
		};

		FileUploadView fuv = this.appContext.getSingleView(FileUploadView.class);
		fuv.fileUpload(file, callBackAction);
	}
}
