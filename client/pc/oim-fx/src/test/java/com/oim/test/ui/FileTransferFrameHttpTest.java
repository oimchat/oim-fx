/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import com.oim.common.component.file.action.FileAction;
import com.oim.core.common.component.FileHttpUpload;
import com.oim.core.common.component.file.FileInfo;
import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.FileTransferFrame;
import com.oim.ui.fx.classics.file.FileUpItem;
import com.only.common.util.OnlyNumberUtil;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class FileTransferFrameHttpTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		FileTransferFrame l = new FileTransferFrame();
		l.show();

		// String name = "123.mp4";
		// String size = "134mb/450mb";
		// String speed = "124kb/s";
		Image image = ImageBox.getImageClassPath("/resources/common/images/file/default.png");
		FileUpItem fui = new FileUpItem();

		fui.setImage(image);

		l.addNode(fui);

		FileAction<FileInfo> fileAction = new FileAction<FileInfo>() {

			@Override
			public void progress(long speed, long size, long up, double percentage) {

				System.out.println("speed:" + (speed) + "mb/s");
				System.out.println("size:" + size);
				System.out.println("up:" + up);
				System.out.println("percentage:" + percentage);

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
						fui.setSpeed(speedText);
						BigDecimal bg = new BigDecimal(percentage).setScale(2, RoundingMode.UP);
						fui.setProgress(bg.doubleValue());
					}
				});
			}

			@Override
			public void success(FileInfo fileData) {
				System.out.println("success id=" + fileData.getId());
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						fui.setProgress(1.0d);
					}
				});
			}

			@Override
			public void lost(FileInfo f) {

			}

		};
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", "123456");
		String filepath = "E:/Videos/film/[阳光电影www.ygdy8.com].爱宠大机密.HD.720p.中英双字幕.rmvb";
		String http = "http://127.0.0.1:8010/oim-file-server/file/upload.do";
		FileHttpUpload fhu = new FileHttpUpload();
		File file = new File(filepath);
		long size = file.length();
		String sizeText = "0MB";
		if (size < 1024) {
			sizeText = size + "B/s";
		} else if (1024 <= size && size < (1024 * 1024)) {
			String s = OnlyNumberUtil.format(((double) size / 1024d));
			sizeText = s + "KB/s";
		} else if (1024 <= size && size < (1024 * 1024 * 1024)) {
			String s = OnlyNumberUtil.format(((double) size / (double) (1024 * 1024)));
			sizeText = s + "MB/s";
		} else {
			String s = OnlyNumberUtil.format(((double) size / (double) (1024 * 1024 * 1024)));
			sizeText = s + "GB/s";
		}
		fui.setName(file.getName());
		fui.setSize(sizeText);

		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations = 0;
				fhu.upload(http, file, dataMap, fileAction);
				return iterations;
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
