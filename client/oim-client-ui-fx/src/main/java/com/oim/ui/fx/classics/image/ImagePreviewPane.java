package com.oim.ui.fx.classics.image;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;

/**
 * @author XiaHui
 * @date 2017年5月27日 上午8:47:33
 */
public class ImagePreviewPane extends StackPane {

	StackPane centerPane = new StackPane();
	ScrollPane scrollPane = new ScrollPane();
	AnchorPane imagePane = new AnchorPane();
	ImageView imageView = new ImageView();
	double imageW = 0;
	double imageH = 0;
	Image image;

	public ImagePreviewPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(scrollPane);
		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setContent(imagePane);
		imagePane.getChildren().add(imageView);
	}

	private void iniEvent() {
		scrollPane.setOnScroll(e -> {
			// System.out.println("setOnScroll");
		});
		scrollPane.setOnScrollFinished(e -> {
			System.out.println("setOnScrollFinished");
		});
		scrollPane.setOnScrollStarted(e -> {
			System.out.println("setOnScrollStarted");
		});
		imageView.setOnScroll(e -> {
			double deltaX = e.getDeltaX();
			double deltaY = e.getDeltaY();

			if (deltaX < 0 || deltaY < 0) {
				// System.out.println("down");
				// setImageUp(false);
			} else {
				// System.out.println("up");
				// setImageUp(true);
			}
		});
	}

	public void setImage(Image value) {
		imageView.setImage(value);
		if (value != null) {
			image = value;
			setSize(value);
		}
	}

	private void setSize(Image value) {
		imageW = value.getWidth();
		imageH = value.getHeight();

		imageW = value.getRequestedWidth();
		imageH = value.getRequestedHeight();

		// System.out.println(imagePane.getWidth());
		// imageView.setFitWidth(imageW);
		// imageView.setFitHeight(imageH);
	}

	public void setImageUp(boolean up) {
		imageView.setFitHeight(up ? (imageView.getFitHeight() + 100) : (imageView.getFitHeight() - 100));
		imageView.setFitWidth(up ? (imageView.getFitWidth() + 100) : (imageView.getFitWidth() - 100));
		System.out.println(imageView.getFitWidth());
	}
}
