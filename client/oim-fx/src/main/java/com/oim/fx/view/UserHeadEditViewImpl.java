package com.oim.fx.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.oim.fx.common.component.WaitingPane;
import com.oim.fx.ui.list.HeadImagePanel;
import com.oim.ui.fx.classics.CommonStage;
import com.oim.ui.fx.classics.head.HeadListPane;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.app.base.view.AbstractView;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;

/**
 * @author: XiaHui
 * @date: 2017年4月11日 下午12:07:41
 */
public class UserHeadEditViewImpl extends AbstractView implements UserHeadEditView {

	CommonStage frame;
	HeadListPane p;
	Alert alert;

	public UserHeadEditViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new CommonStage();
				p = new HeadListPane();
				initEvent();
			}
		});
	}

	private void initEvent() {

		alert = new Alert(AlertType.CONFIRMATION, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(frame);
		alert.getDialogPane().setContentText("确定选择");
		alert.getDialogPane().setHeaderText(null);

		frame.setWidth(430);
		frame.setHeight(580);
		frame.setResizable(false);
		frame.setTitlePaneStyle(2);
		frame.setTitle("选择头像");

		frame.setCenter(p);
		frame.show();


		p.setPrefWrapLength(400);
		p.showWaiting(true, WaitingPane.show_waiting);
		appContext.add(new ExecuteTask() {

			@Override
			public void execute() {
				init(p);
			}
		});
	}

	private void init(HeadListPane p) {
		List<HeadImagePanel> list = new ArrayList<HeadImagePanel>();
		for (int i = 1; i < 102; i++) {
			//Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + "_100.gif");
			String pathString = new File("Resources/Images/Head/User/" + i + "_100.gif").toURI().toString();
			Image image = new Image(pathString, false);
			String value = i + "";
			HeadImagePanel hp = new HeadImagePanel();
			hp.setHeadSize(60);
			hp.setImage(image);
			hp.setOnMouseClicked(m -> {
				select(image, value);
			});
			list.add(hp);

		}

		for (int i = 173; i < 265; i++) {
			//Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + "_100.gif");
			String pathString = new File("Resources/Images/Head/User/" + i + "_100.gif").toURI().toString();
			Image image = new Image(pathString, false);
			
			String value = i + "";
			HeadImagePanel hp = new HeadImagePanel();
			hp.setHeadSize(60);
			hp.setImage(image);
			hp.setOnMouseClicked(m -> {
				select(image, value);
			});
			list.add(hp);

		}
		p.showWaiting(false, WaitingPane.show_waiting);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				p.setNodeList(list);
			}
		});

	}

	private void select(Image image, String value) {
		alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {
					System.out.println(value);
				});

	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					frame.show();
					frame.toFront();
				} else {
					frame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return frame.isShowing();
	}
}
