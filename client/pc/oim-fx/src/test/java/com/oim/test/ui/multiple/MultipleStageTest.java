/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.multiple;

import java.util.Random;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.list.HeadItem;
import com.oim.fx.ui.list.ListNodePanel;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.ui.fx.multiple.MultipleStage;
import com.oim.ui.fx.succinct.chat.ChatItemPane;
import com.oim.ui.fx.succinct.list.ListItemPane;
import com.oim.ui.fx.succinct.list.ListNodePane;
import com.oim.ui.fx.succinct.main.MainPane;
import com.oim.ui.fx.succinct.main.PersonalPane;
import com.only.common.util.OnlyDateUtil;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class MultipleStageTest extends Application {

	MultipleStage stage = new MultipleStage();
	MainPane mainPane = new MainPane();
	BorderPane rootBorderPane = new BorderPane();

	@Override
	public void start(Stage primaryStage) {
		rootBorderPane.setCenter(mainPane);

		stage.setTitle("WeChat");
		stage.setRadius(1);
		stage.getScene().getStylesheets().add(this.getClass().getResource("/multiple/css/main.css").toString());
		stage.setCenter(rootBorderPane);
		stage.show();
		initTest();
		initUserList();
	}

	private void initTest() {
		PersonalPane pp = mainPane.getPersonalPane();
		Image headImage = ImageBox.getImagePath("Resources/Images/Head/User/85_100.gif", 60, 60);
		pp.setHeadImage(headImage);
		pp.setName("瓦沙啥");
	}

	
	private void initUserList() {
        Random random = new Random();

        Image normalImage = ImageBox.getImageClassPath("/multiple/images/main/tab/contacts_normal.png");
        Image hoverImage = ImageBox.getImageClassPath("/multiple/images/main/tab/contacts_normal.png");
        Image selectedImage = ImageBox.getImageClassPath("/multiple/images/main/tab/contacts_active.png");
        ListRootPanel userList = new ListRootPanel();
        mainPane.addTab(normalImage, hoverImage, selectedImage, userList);

        ListNodePane[] teamNode = new ListNodePane[5];
        for (int j = 0; j < 5; j++) {
            teamNode[j] = new ListNodePane();
            teamNode[j].setText("我的好友" + j);

            for (int i = 173; i < 202; i++) {
                ListItemPane head = new ListItemPane();
                Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + i + ".png", 40, 40);
                head.setHeadImage(image);
                head.setName("(哈加额)");
                teamNode[j].addItem(head);
            }
            userList.addNode(teamNode[j]);
        }

        normalImage = ImageBox.getImageClassPath("/multiple/images/main/tab/me_normal.png");
        hoverImage = ImageBox.getImageClassPath("/multiple/images/main/tab/me_normal.png");
        selectedImage = ImageBox.getImageClassPath("/multiple/images/main/tab/me_active.png");

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
        normalImage = ImageBox.getImageClassPath("/multiple/images/main/tab/chat_normal.png");
        hoverImage = ImageBox.getImageClassPath("/multiple/images/main/tab/chat_normal.png");
        selectedImage = ImageBox.getImageClassPath("/multiple/images/main/tab/chat_active.png");

        ListRootPanel lastRoot = new ListRootPanel();
        mainPane.addTab(normalImage, hoverImage, selectedImage, lastRoot);

        
        for (int j = 173; j < 202; j++) {

        	ChatItemPane head = new ChatItemPane();
            Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + (j + 1) + ".png", 40, 40);

            head.setHeadImage(image);

            head.setName("w$323&&a哈哈是");
            head.setText("哈哈哈，又有新闻了顺丰顺丰顺丰发7777777777777xxxxx");
            head.setTime(OnlyDateUtil.getCurrentTime());

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
