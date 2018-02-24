/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import org.apache.commons.lang3.StringUtils;

import com.oim.common.util.HttpUrlUtil;
import com.oim.controlsfx.Notifications;
import com.oim.ui.fx.classics.NoticeFrame;
import com.oim.ui.fx.classics.WebFrame;
import com.oim.ui.fx.classics.notice.TextNoticeItem;
import com.only.common.util.OnlyDateUtil;
import com.onlyxiahui.im.message.data.notice.TextNotice;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Only
 */
public class NoticeFrameTest extends Application {

	NoticeFrame frame = new NoticeFrame();
	int size = 10;
	WebFrame webFrame=new WebFrame();
	
	@Override
	public void start(Stage primaryStage) {
		frame.show();

		String text="马上要开团了点击打开dassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssfsdsdfsdfsdfsdfsdfsdfdsfsdfdfsf";
		for (int i = 0; i < size; i++) {
			TextNoticeItem tni = new TextNoticeItem();
			tni.setTime(OnlyDateUtil.getCurrentDateTime());
			tni.setTitle("打团通告");
			tni.setContent(text);
			tni.setOnOpenAction(a -> {
				//HttpUrlUtil.open("www.baidu.com");
				addNotifications(TextNotice.open_type_browser, "www.baidu.com", "打团通告","马上要开团了点击打开");
			});
			frame.addItem(tni);
		}
	}

	private void addNotifications(String openType, String url, String title, String content) {
		Notifications notificationBuilder = Notifications.create()
				.title(title)
				.text(content)
				.textColor("cc3333")
				.hideAfter(Duration.seconds(31536000))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent a) {
						if (StringUtils.isNotBlank(url)) {
							if (TextNotice.open_type_app.equals(openType)) {
								if(!webFrame.isShowing()) {
									webFrame.show();
								}
								webFrame.load(url);
							} else {
								HttpUrlUtil.open(url);
							}
						}
					}
				});

		notificationBuilder.showInformation();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
