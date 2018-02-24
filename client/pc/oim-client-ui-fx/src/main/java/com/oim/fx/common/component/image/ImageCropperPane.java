package com.oim.fx.common.component.image;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-31 15:07:53
 */
public class ImageCropperPane extends StackPane {

	BorderPane baseBorderPane = new BorderPane();

	StackPane centerStackPane = new StackPane();

	// StackPane cropperStackPane = new StackPane();
	ScrollPane cropperScrollPane = new ScrollPane();
	AnchorPane cropperAnchorPane = new AnchorPane();

	StackPane imageStackPane = new StackPane();
	ImageView imageView = new ImageView();

	StackPane coverStackPane = new StackPane();
	ImageView coverImageView = new ImageView();

	BorderPane bottomBorderPane = new BorderPane();

	Slider slider = new Slider();
	BorderPane sliderBorderPane = new BorderPane();

	boolean newImage = false;
	Image image;
	BorderPane rotateBorderPane = new BorderPane();
	IconButton plusButton = new IconButton();
	IconButton minusButton = new IconButton();

	IconButton leftRotateButton = new IconButton();
	IconButton rightRotateButton = new IconButton();

	public ImageCropperPane() {
		initComponent();
		initEvent();
	}

	private void initComponent() {

		imageView.setPreserveRatio(true);
		imageStackPane.getChildren().add(imageView);
		cropperAnchorPane.getChildren().add(imageStackPane);

		cropperScrollPane.setBackground(Background.EMPTY);
		cropperScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		cropperScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		cropperScrollPane.setContent(cropperAnchorPane);
		// cropperStackPane.getChildren().add(cropperScrollPane);

		coverStackPane.getChildren().add(coverImageView);
		coverStackPane.setMouseTransparent(true);

		centerStackPane.getChildren().add(cropperScrollPane);
		centerStackPane.getChildren().add(coverStackPane);

		leftRotateButton.setNormalImage(ImageBox.getImageClassPath("/classics/images/cropper/bighead_editor_rotate_left_normal.png"));
		leftRotateButton.setHoverImage(ImageBox.getImageClassPath("/classics/images/cropper/bighead_editor_rotate_left_hover.png"));
		leftRotateButton.setPressedImage(ImageBox.getImageClassPath("/classics/images/cropper/bighead_editor_rotate_left_down.png"));

		rightRotateButton.setNormalImage(ImageBox.getImageClassPath("/classics/images/cropper/bighead_editor_rotate_right_normal.png"));
		rightRotateButton.setHoverImage(ImageBox.getImageClassPath("/classics/images/cropper/bighead_editor_rotate_right_hover.png"));
		rightRotateButton.setPressedImage(ImageBox.getImageClassPath("/classics/images/cropper/bighead_editor_rotate_right_down.png"));

		rotateBorderPane.setPadding(new Insets(0, 0, 0, 20));
		rotateBorderPane.setLeft(leftRotateButton);
		rotateBorderPane.setRight(rightRotateButton);

		slider.setMin(100);

		sliderBorderPane.setCenter(slider);

		bottomBorderPane.setCenter(sliderBorderPane);
		bottomBorderPane.setRight(rotateBorderPane);

		baseBorderPane.setCenter(centerStackPane);
		baseBorderPane.setBottom(bottomBorderPane);

		this.getChildren().add(baseBorderPane);
	}

	private double initX;
	private double initY;

	private double initW;
	private double initH;

	private void initEvent() {
		cropperAnchorPane.heightProperty().addListener(h -> {
			if (newImage) {
				newImage = false;

				double min = coverStackPane.getWidth();
				double max = cropperAnchorPane.getWidth();

				double temp = max - min;

				if (temp < 100) {
					max = min + (100 - temp);
				}

				slider.setMax(max);
				slider.setMin(min - 2);
				slider.setValue(slider.getMax());
			}
		});

		slider.valueProperty().addListener(l -> {
			imageView.setFitWidth(slider.getValue());
		});

		imageStackPane.setOnMousePressed((MouseEvent me) -> {
			initX = me.getX() - imageStackPane.getLayoutX();
			initY = me.getY() - imageStackPane.getLayoutY();
			initW = imageStackPane.getWidth();
			initH = imageStackPane.getHeight();
		});

		imageStackPane.setOnMouseDragged((MouseEvent me) -> {

			double w = cropperScrollPane.getWidth();
			double h = cropperScrollPane.getHeight();

			double x = me.getX() - initX;
			double y = me.getY() - initY;

			boolean mx = true;
			boolean my = true;

			if (x > 0) {
				mx = false;
			} else if (initW < (w - x)) {
				mx = false;
			}

			if (y > 0) {
				my = false;
			} else if (initH < (h - y)) {
				my = false;
			}

			if (mx) {
				imageStackPane.setLayoutX(x);
			}
			if (my) {
				imageStackPane.setLayoutY(y);
			}
			me.consume();
		});

		leftRotateButton.setOnAction(a -> {
			double rotate = imageView.getRotate();
			rotate = rotate - 90;
			if (rotate <= -360) {
				rotate = 0;
			}
			imageView.setRotate(rotate);
		});
		rightRotateButton.setOnAction(a -> {
			double rotate = imageView.getRotate();
			rotate = rotate + 90;
			if (rotate >= 360) {
				rotate = 0;
			}
			imageView.setRotate(rotate);
		});
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
	}

	public void setCoverImage(Image coverImage) {
		coverImageView.setImage(coverImage);
	}

	public void setCoverSize(double width, double height) {
		coverImageView.setFitWidth(width);
		coverImageView.setFitHeight(height);
		centerStackPane.setPrefSize(width, height);
		centerStackPane.setMaxSize(width, height);

		baseBorderPane.setMaxWidth(width);
	}

	public void setImage(Image image) {
		this.image = image;
		if (null != image) {
			newImage = true;
			imageView.setFitWidth(image.getWidth());
		}
		imageView.setImage(image);
	}

	public boolean hasImage() {
		return this.image != null;
	}

	public Image getImage() {
		Rectangle2D viewport = new Rectangle2D(0, 0, cropperScrollPane.getWidth(), cropperScrollPane.getHeight());
		SnapshotParameters p = new SnapshotParameters();
		p.setViewport(viewport);
		WritableImage image = cropperScrollPane.snapshot(p, null);
		return image;// imageView.getImage();
	}
}
