package com.oim.core.business.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.oim.common.component.file.FileHttpDownload;
import com.oim.common.component.file.action.FileAction;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.sender.FileSender;
import com.oim.core.common.component.FileHttpUpload;
import com.oim.core.common.component.file.FileInfo;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.im.bean.UserData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月15日 下午7:52:44
 * @version 0.0.1
 */
public class FileManager extends AbstractManager {

	private String fileServerAddress;

	FileHttpUpload fhu = new FileHttpUpload();
	FileHttpDownload fhd = new FileHttpDownload();

	public FileManager(AppContext appContext) {
		super(appContext);
	}

	public void uploadFile(final File file,final  FileAction<FileInfo> fileAction) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		String userId = (null == user) ? null : user.getId();
		final Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", userId);
		final ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				String http = fileServerAddress + "/file/upload.do";
				fhu.upload(http, file, dataMap, fileAction);
			}
		};
		if (null == fileServerAddress || "".equals(fileServerAddress)) {
			final FileInfo fileInfo=new FileInfo();
			fileInfo.setFile(file);
			DataBackAction dataBackAction = new DataBackAction() {

				@Override
				public void lost() {
					fileAction.lost(fileInfo);
				}

				@Override
				public void timeOut() {
					fileAction.lost(fileInfo);
				}

				@Back
				public void back(Info info, @Define("fileServerUrl") String fileServerUrl) {
					fileServerAddress = fileServerUrl;
					if (info.isSuccess()) {
						appContext.add(et);
					} else {
						fileAction.lost(fileInfo);
					}
				}
			};
			FileSender rs = this.appContext.getSender(FileSender.class);
			rs.getFileServer(dataBackAction);
		} else {
			appContext.add(et);
		}
	}

	public void downloadFile(final File saveFile, final String id,final  FileAction<File> fileAction) {
		final ExecuteTask et = new ExecuteTask() {
			@Override
			public void execute() {
				String http = fileServerAddress + "/file/download.do?id=" + id;
				fhd.download(http, saveFile, fileAction);
			}
		};
		if (null == fileServerAddress || "".equals(fileServerAddress)) {
			DataBackAction dataBackAction = new DataBackAction() {

				@Override
				public void lost() {
					fileAction.lost(saveFile);
				}

				@Override
				public void timeOut() {
					fileAction.lost(saveFile);
				}

				@Back
				public void back(Info info, @Define("fileServerUrl") String fileServerUrl) {
					fileServerAddress = fileServerUrl;
					if (info.isSuccess()) {
						appContext.add(et);
					} else {
						fileAction.lost(saveFile);
					}
				}
			};
			FileSender rs = this.appContext.getSender(FileSender.class);
			rs.getFileServer(dataBackAction);
		} else {
			appContext.add(et);
		}
	}
}
