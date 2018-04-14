/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.fx.v1;

import java.util.Random;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.list.HeadItem;
import com.oim.fx.ui.list.ListNodePanel;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.ui.fx.classics.MainStage;
import com.oim.ui.fx.classics.main.MainPane;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class MainStageTest extends Application {
	
	MainStage stage = new MainStage();
	MainPane mainPane = new MainPane();
	@Override
	public void start(Stage primaryStage) {
		stage.setCenter(mainPane);
		stage.show();
		initTest();
    	initUserList();
	}
	/**
     * 测试数据
     */
    private void initTest() {
        ///////////////////////////////////////用户信息
        Image statusImage = ImageBox.getImageClassPath("/resources/common/images/status/online.png");
        Image headImage = ImageBox.getImagePath("Resources/Images/Head/User/85_100.gif", 60, 60);
        mainPane.setHeadImage(headImage);
        mainPane.setStatusImage(statusImage);
        mainPane.setNickname("瓦沙啥");
        mainPane.setText("好多想买的东西，也就只能是想买的东西了。");

        IconPane iconButton = null;

        /////////////////////////////////////////////function
        Image normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn_normal.png");
        Image hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn2_down.png");
        Image pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn_highlight.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainPane.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainPane.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_highlight.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainPane.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainPane.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/mycollection_mainpanel.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/myCollection_mainpanel_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/myCollection_mainpanel_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainPane.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find_down.png");

        iconButton = new IconPane("查找", normalImage, hoverImage, pressedImage);
        mainPane.addFunctionIcon(iconButton);


        /////////////////////////////////////////////////////////////////////app
        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainPane.addRightFunctionIcon(iconButton);
    }

    private void initUserList() {
        Random random = new Random();

        Image normalImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_contacts_normal.png");
        Image hoverImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_contacts_hover.png");
        Image selectedImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_contacts_selected.png");
        ListRootPanel userList = new ListRootPanel();
        mainPane.addTab(normalImage, hoverImage, selectedImage, userList);

        ListNodePanel[] teamNode = new ListNodePanel[5];
        for (int j = 0; j < 5; j++) {
            teamNode[j] = new ListNodePanel();
            teamNode[j].setText("我的好友" + j);

            for (int i = 0; i < 5; i++) {
                int index = random.nextInt(100) + 1;
                HeadItem head = new HeadItem();
                Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + index + ".png", 40, 40);
                head.setHeadImage(image);
                head.setRemark("女神经" + (j + 1));
                head.setNickname("(哈加额)");
                head.setStatus("[2G]");
                head.setShowText("哈哈哈，又有新闻了");

                Image iconImage = ImageBox.getImagePath("Resources/Images/Default/Status/FLAG/Big/imonline.png");
                IconPane iconButton = new IconPane(iconImage);
                head.addBusinessIcon(iconButton);

                head.setOnMouseClicked(m->{
                	if (m.getClickCount() == 2) {
        				head.setPulse(!head.isPulse());
        			}
                });
                
                teamNode[j].addItem(head);
            }
            userList.addNode(teamNode[j]);
        }

        normalImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_group_normal.png");
        hoverImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_group_hover.png");
        selectedImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_group_selected.png");

        ListRootPanel groupRoot = new ListRootPanel();
        mainPane.addTab(normalImage, hoverImage, selectedImage, groupRoot);

        ListNodePanel[] groupNode = new ListNodePanel[5];
        for (int j = 0; j < 5; j++) {
            groupNode[j] = new ListNodePanel();
            groupNode[j].setText("我的群" + j);
            groupNode[j].setNumberText("[5]");
            for (int i = 0; i < 5; i++) {

                int index = random.nextInt(100) + 1;
                HeadItem head = new HeadItem();
                Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + index + ".png", 40, 40);
                head.setHeadImage(image);

                head.setRemark("女神经" + (j + 1));
                head.setNickname("(哈加额)");
                head.setStatus("[2G]");
                head.setShowText("哈哈哈，又有新闻了");
                Image iconImage = ImageBox.getImagePath("Resources/Images/Default/Status/FLAG/Big/imonline.png");
                IconPane iconButton = new IconPane(iconImage);
                head.addBusinessIcon(iconButton);

                groupNode[j].addItem(head);
            }
            groupRoot.addNode(groupNode[j]);
        }
//
        normalImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_last_normal.png");
        hoverImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_last_hover.png");
        selectedImage = ImageBox.getImageClassPath("/v1/images/main/panel/icon_last_selected.png");

        ListRootPanel lastRoot = new ListRootPanel();
        mainPane.addTab(normalImage, hoverImage, selectedImage, lastRoot);

        for (int j = 0; j < 15; j++) {

            HeadItem head = new HeadItem();
            Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + (j + 1) + ".png", 40, 40);

            head.setHeadImage(image);

            head.setRemark("女神经" + (j + 1));
            head.setNickname("(哈加额)");
            head.setStatus("[2G]");
            head.setShowText("哈哈哈，又有新闻了");
            Image iconImage = ImageBox.getImagePath("Resources/Images/Default/Status/FLAG/Big/imonline.png");
            IconPane iconButton = new IconPane(iconImage);
            head.addBusinessIcon(iconButton);

            lastRoot.addNode(head);
        }
    }
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
