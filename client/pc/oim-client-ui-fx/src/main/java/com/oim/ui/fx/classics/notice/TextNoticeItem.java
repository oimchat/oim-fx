package com.oim.ui.fx.classics.notice;

import com.oim.fx.common.box.ImageBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author XiaHui
 * @date 2017年8月18日 下午3:41:46
 */
public class TextNoticeItem extends BorderPane {

	HBox titlePane = new HBox();
	Label timeLabel = new Label();
	Label titleLabel = new Label();

	Label contentLabel = new Label();

	Label imageLabel = new Label();
	ImageView imageView = new ImageView();

	HBox box = new HBox();

	HBox bottomBox = new HBox();
	Button openButton = new Button();

	public TextNoticeItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTop(titlePane);
		this.setCenter(box);
		this.setBottom(bottomBox);
		this.setStyle("-fx-border-color:#e6e6e6;-fx-border-width: 1px;-fx-background-color:rgba(255, 255, 255, 0.9)");

		Image image = ImageBox.getImageClassPath("/resources/common/images/info/sysmessagebox_inforFile.png");
		imageView.setImage(image);
		imageLabel.setGraphic(imageView);
		
		titlePane.setSpacing(10);
		titlePane.getChildren().add(timeLabel);
		titlePane.getChildren().add(titleLabel);

		contentLabel.setWrapText(true);
		
		box.getChildren().add(imageLabel);
		box.getChildren().add(contentLabel);
		box.setSpacing(2);
		box.setPadding(new Insets(10, 0, 0, 5));
		// Hyperlink link = new Hyperlink("www.oracle.com");
		// link.setWrapText(true);

		openButton.setText("打开");
		openButton.setPrefWidth(80);
		openButton.setVisible(false);

		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(openButton);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public void setContent(String content) {
		contentLabel.setText(content);
	}

	public void setTime(String time) {
		timeLabel.setText(time);
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public void setOnOpenAction(EventHandler<ActionEvent> value) {
		openButton.setOnAction(value);
		if (null != value) {
			openButton.setVisible(true);
		}
	}
}
