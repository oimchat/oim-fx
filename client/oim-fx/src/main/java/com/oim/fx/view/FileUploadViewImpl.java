package com.oim.fx.view;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.oim.common.component.file.action.FileAction;
import com.oim.core.business.manager.FileManager;
import com.oim.core.business.view.FileUploadView;
import com.oim.core.common.action.CallBackAction;
import com.oim.core.common.component.file.FileInfo;
import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.FileTransferFrame;
import com.oim.ui.fx.classics.file.FileUpItem;
import com.only.common.util.OnlyNumberUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.message.data.FileData;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * @author XiaHui
 * @date 2017年6月2日 下午9:35:57
 */
public class FileUploadViewImpl extends AbstractView implements FileUploadView {

	FileTransferFrame ftf;// = new FileTransferFrame();

	public FileUploadViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ftf = new FileTransferFrame();
				ftf.setLabelTitle("文件发送");
			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showPrompt(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isShowing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showWaiting(boolean show) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fileUpload(File file, CallBackAction<FileData> callBackAction) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!ftf.isShowing()) {
					ftf.show();
				}
				String fileName=file.getName();
				Image image = ImageBox.getImageClassPath("/oim/common/images/file.icon/default.png");
				FileUpItem fui = new FileUpItem();
				fui.setImage(image);
				fui.setName(fileName);
				//fui.setSize(value);
				
				fui.setOnCloseAction(c -> {
					ftf.removeNode(fui);
				});
				ftf.addNode(fui);

				FileAction<FileInfo> fileAction = new FileAction<FileInfo>() {

					@Override
					public void progress(long speed, long fileSize, long finishSize, double progress) {

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String speedText = "0MB/s";
								if (speed < 1024) {
									speedText = speed + "B/s";
								} else if (1024 <= speed && speed < (1024 * 1024)) {
									String s = OnlyNumberUtil.format(((double) speed / 1024d));
									speedText = s + "KB/s";
								} else {
									String s = OnlyNumberUtil.format(((double) speed / (1048576d)));
									speedText = s + "MB/s";
								}

								String fileSizeText = "0MB";
								if (fileSize < 1024) {
									fileSizeText = fileSize + "B";
								} else if (1024 <= fileSize && fileSize < (1024 * 1024)) {
									String s = OnlyNumberUtil.format(((double) fileSize / 1024d));
									fileSizeText = s + "KB";
								} else if (1024 * 1024 <= fileSize && fileSize < (1024 * 1024 * 1024)) {
									String s = OnlyNumberUtil.format(((double) fileSize / (1048576d)));
									fileSizeText = s + "MB";
								} else {
									String s = OnlyNumberUtil.format(((double) fileSize / (1073741824d)));
									fileSizeText = s + "GB";
								}

								String finishSizeText = "0MB";
								if (finishSize < 1024) {
									finishSizeText = finishSize + "B";
								} else if (1024 <= finishSize && finishSize < (1024 * 1024)) {
									String s = OnlyNumberUtil.format(((double) finishSize / 1024d));
									finishSizeText = s + "KB";
								} else if (1024 * 1024 <= finishSize && finishSize < (1024 * 1024 * 1024)) {
									String s = OnlyNumberUtil.format(((double) finishSize / (1048576d)));
									finishSizeText = s + "MB";
								} else {
									String s = OnlyNumberUtil.format(((double) finishSize / (1073741824d)));
									finishSizeText = s + "GB";
								}

								fui.setSpeed(speedText);
								fui.setSize(finishSizeText + "/" + fileSizeText);
								BigDecimal bg = new BigDecimal(progress).setScale(2, RoundingMode.UP);
								fui.setProgress(bg.doubleValue());
							}
						});
					}

					@Override
					public void success(FileInfo fileInfo) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								fui.setProgress(1.0d);
							}
						});
						//File file=fileInfo.getFile();
						FileData fileData=new FileData();
						fileData.setId(fileInfo.getId());
						fileData.setName(file.getName());
						fileData.setSize(file.length());
						callBackAction.execute(fileData);
					}

					@Override
					public void lost(FileInfo fileInfo) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								fui.setProgress(0.1d);
								ftf.showPrompt("文件发送失败！");
							}
						});
					}
				};
				FileManager fm = appContext.getManager(FileManager.class);
				fm.uploadFile(file, fileAction);
			}
		});
	}
}
