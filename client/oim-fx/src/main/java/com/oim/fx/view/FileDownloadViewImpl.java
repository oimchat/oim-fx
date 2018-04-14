package com.oim.fx.view;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.oim.common.component.file.action.FileAction;
import com.oim.core.business.manager.FileManager;
import com.oim.core.business.view.FileDownloadView;
import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.FileTransferFrame;
import com.oim.ui.fx.classics.file.FileDownItem;
import com.only.common.util.OnlyNumberUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.message.data.FileData;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * @author XiaHui
 * @date 2017年6月2日 下午10:30:12
 */
public class FileDownloadViewImpl extends AbstractView implements FileDownloadView {

	FileTransferFrame ftf;// = new FileTransferFrame();
	private FileChooser fileChooser;

	public FileDownloadViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ftf = new FileTransferFrame();
				ftf.setLabelTitle("接受文件");
				fileChooser = new FileChooser();
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
	public void fileDownload(FileData fileData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!ftf.isShowing()) {
					ftf.show();
				}
				Image image = ImageBox.getImageClassPath("/oim/common/images/file.icon/default.png");
				long fileSize = fileData.getSize();

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

				FileDownItem fdi = new FileDownItem();
				fdi.setName(fileData.getName());
				fdi.setImage(image);
				fdi.setSize(fileSizeText);
				fdi.showSaveAs(true);

				FileAction<File> fileAction = new FileAction<File>() {

					@Override
					public void progress(long speed, long size, long finishSize, double progress) {

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
								fdi.setSpeed(speedText);
								BigDecimal bg = new BigDecimal(progress).setScale(2, RoundingMode.UP);
								fdi.setProgress(bg.doubleValue());
							}
						});
					}

					@Override
					public void success(File file) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								fdi.setProgress(1.0d);
							}
						});
					}

					@Override
					public void lost(File file) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								fdi.setProgress(0.1d);
								fdi.showSaveAs(true);
								ftf.showPrompt("文件接受失败！");
							}
						});
					}
				};

				fdi.setOnSaveAction(a -> {
					fileChooser.setInitialFileName(fileData.getName());
					File file = fileChooser.showSaveDialog(ftf);
					if (null != file) {
						fdi.showSaveAs(false);
						FileManager fm = appContext.getManager(FileManager.class);
						fm.downloadFile(file, fileData.getId(), fileAction);
					}
				});
				fdi.setOnCloseAction(c -> {
					ftf.removeNode(fdi);
				});
				ftf.addNode(fdi);
			}
		});
	}
}
