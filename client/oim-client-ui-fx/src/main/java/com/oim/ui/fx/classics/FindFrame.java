package com.oim.ui.fx.classics;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.find.FindGroupPane;
import com.oim.ui.fx.classics.find.FindUserPane;
import com.only.fx.common.component.FlatTabPane;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author: XiaHui
 * @date: 2016年12月19日 下午4:04:43
 */
public class FindFrame extends ClassicsStage{

	FlatTabPane tabPanel = new FlatTabPane();
	FindUserPane findUserPanel = new FindUserPane();
	FindGroupPane findGroupPanel = new FindGroupPane();

	public FindFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("查找");
		this.setWidth(900);
		this.setHeight(600);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		BorderPane rootBox = new BorderPane();
		this.setCenter(rootBox);
		this.setResizable(false);

		HBox hBox = new HBox();
		hBox.setMinHeight(50);
		// rootBox.getChildren().add(hBox);
		// rootBox.getChildren().add(tabPanel);
		rootBox.setTop(hBox);
		rootBox.setCenter(tabPanel);

		Image normalImage = ImageBox.getImageClassPath("/classics/images/find/icon_contacts_normal@2x.png");
		Image hoverImage = ImageBox.getImageClassPath("/classics/images/find/icon_contacts_hover@2x.png");
		Image selectedImage = ImageBox.getImageClassPath("/classics/images/find/icon_contacts_selected@2x.png");

		tabPanel.add("找人", Font.font("微软雅黑", 16), Color.WHITE, normalImage, hoverImage, selectedImage, findUserPanel);

		normalImage = ImageBox.getImageClassPath("/classics/images/find/icon_group_normal@2x.png");
		hoverImage = ImageBox.getImageClassPath("/classics/images/find/icon_group_hover@2x.png");
		selectedImage = ImageBox.getImageClassPath("/classics/images/find/icon_group_selected@2x.png");

		tabPanel.add("找群", Font.font("微软雅黑", 16), Color.WHITE, normalImage, hoverImage, selectedImage, findGroupPanel);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public FindUserPane getFindUserPane() {
		return findUserPanel;
	}

	public FindGroupPane getFindGroupPane() {
		return findGroupPanel;
	}

	public void selectedTab(int index) {
		tabPanel.selected(index);
	}

}
