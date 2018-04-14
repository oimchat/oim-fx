package com.oim.ui.fx.bine;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.BaseStage;
import com.oim.fx.common.component.IconToggleButton;
import com.oim.fx.ui.list.HeadImagePanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 类似微信的主界面
 * 
 * @author: XiaHui
 * @date 2017-10-25 17:54:22
 */
public class MainStage extends BaseStage {

	BorderPane borderPane = new BorderPane();

	HeadImagePanel head = new HeadImagePanel();
	BorderPane tabPane = new BorderPane();
	VBox tabBox = new VBox();

	VBox box = new VBox();

	ToggleGroup group = new ToggleGroup();

	public MainStage() {
		initComponent();
	}

	/**
	 * 初始化ui界面各个组件
	 * 
	 * @author: XiaHui
	 * @createDate: 2017-10-25 17:54:45
	 * @update: XiaHui
	 * @updateDate: 2017-10-25 17:54:45
	 */
	private void initComponent() {
		this.setTitle("OIM");
		// this.setTitlePaneStyle(2);
		this.setWidth(900);
		this.setHeight(560);
		this.setMinWidth(1000);
		this.setMinHeight(530);
		// this.setMaxWidth(610);
		this.setRadius(5);
		this.setCenter(borderPane);

		borderPane.setLeft(tabPane);

		head.setHeadSize(40);
		head.setHeadRadius(8);

		tabBox.setAlignment(Pos.TOP_CENTER);
		tabBox.setPadding(new Insets(30, 0, 0, 0));
		tabBox.setSpacing(30);

		VBox headBox = new VBox();
		headBox.setAlignment(Pos.BOTTOM_CENTER);
		headBox.setPadding(new Insets(8, 1, 1, 1));
		headBox.getChildren().add(head);

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(headBox);

		tabPane.setTop(hBox);
		tabPane.setCenter(tabBox);

		tabPane.setMinWidth(60);
		tabPane.setStyle("-fx-background-color:rgba(44, 50, 59, 0.8)");

	}

	void initEvent() {
		// group.selectedToggleProperty().addListener((ObservableValue<? extends
		// Toggle> observable, Toggle oldValue, Toggle selectedToggle) -> {
		//
		// if (selectedToggle instanceof ImageToggleButton) {
		// Object ud = selectedToggle.getUserData();
		// if (ud instanceof Node) {
		// setSelectedNode((Node) ud);
		// }
		// } else if (oldValue != null) {
		// group.selectToggle(oldValue);
		// }
		// });
		group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {

			if (newValue == null) {
				group.selectToggle(oldValue);
			}
			// System.out.println("group");
		});
	}

	ToggleButton tempTb;
	ChangeListener<Toggle> listener = new ChangeListener<Toggle>() {

		@Override
		public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			if (newValue == null) {
				group.selectToggle(oldValue);
			}
		}
	};

	public void addTab(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		IconToggleButton tb = new IconToggleButton(normalImage, hoverImage, selectedImage);
		tb.setUserData(node);
		tb.setToggleGroup(group);
		tb.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (oldValue != newValue && newValue) {
				setSelectedNode(node);
				redTab(tb, !newValue);
			}
		});
		tabBox.getChildren().add(tb);
		if (tempTb == null) {
			group.selectToggle(tb);
			tempTb = tb;
		}
		group.selectedToggleProperty().removeListener(listener);
		group.selectedToggleProperty().addListener(listener);
		// group.selectedToggleProperty().addListener((ObservableValue<? extends
		// Toggle> observable, Toggle oldValue, Toggle newValue) -> {
		//
		// if (newValue == null) {
		// group.selectToggle(oldValue);
		// }
		// System.out.println("addTab");
		// });
	}

	public void setHeadImage(Image image) {
		head.setImage(image);
	}

	public void setSelectedNode(Node node) {
		borderPane.setCenter(node);
	}

	public void selectedTab(int index) {
		int size = tabBox.getChildren().size();
		if (index < size) {
			Node node = tabBox.getChildren().get(index);
			if (node instanceof ToggleButton) {
				group.selectToggle((ToggleButton) node);
			}
		}
	}

	public void redTab(int index, boolean show) {

		int size = tabBox.getChildren().size();
		if (index < size) {
			Node node = tabBox.getChildren().get(index);
			if (node instanceof IconToggleButton) {
				IconToggleButton tb = (IconToggleButton) node;
				redTab(tb, show);
			}
		}
	}

	public boolean isSelectedTab(int index) {
		boolean show = false;
		int size = tabBox.getChildren().size();
		if (index < size) {
			Node node = tabBox.getChildren().get(index);
			if (node instanceof IconToggleButton) {
				IconToggleButton tb = (IconToggleButton) node;
				show = tb.isSelected();
			}
		}
		return show;
	}

	public void redTab(IconToggleButton tb, boolean show) {
		if (show) {
			Image image = ImageBox.getImageClassPath("/resources/images/common/red/red_12.png");
			tb.setRedImage(image);
		} else {
			tb.setRedImage(null);
		}
	}
}
