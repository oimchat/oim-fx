/*
 * To change mainFrame license header, choose License Headers in Project Properties.
 * To change mainFrame template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;


import java.util.Random;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.MainFrame;
import com.oim.fx.ui.chat.SimpleHead;
import com.oim.fx.ui.list.HeadItem;
import com.oim.fx.ui.list.ListNodePanel;
import com.oim.fx.ui.list.ListRootPanel;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class SimpleHeadFrameTest extends Application {

	MainFrame mainFrame=new MainFrame();
    @Override
    public void start(Stage primaryStage) {
    	mainFrame.show();
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
        mainFrame.setHeadImage(headImage);
        mainFrame.setStatusImage(statusImage);
        mainFrame.setNickname("瓦沙啥");
        mainFrame.setText("好多想买的东西，也就只能是想买的东西了。");

        Image businessImage = ImageBox.getImageClassPath("/resources/main/images/top/1.png");

        IconPane iconButton = new IconPane(businessImage);
        mainFrame.addBusinessIcon(iconButton);

        businessImage = ImageBox.getImageClassPath("/resources/main/images/top/2.png");

        iconButton = new IconPane(businessImage);
        mainFrame.addBusinessIcon(iconButton);

        businessImage = ImageBox.getImageClassPath("/resources/main/images/top/3.png");

        iconButton = new IconPane(businessImage);
        mainFrame.addBusinessIcon(iconButton);

        businessImage = ImageBox.getImageClassPath("/resources/main/images/top/4.png");

        iconButton = new IconPane(businessImage);
        mainFrame.addBusinessIcon(iconButton);

        businessImage = ImageBox.getImageClassPath("/resources/main/images/top/5.png");

        iconButton = new IconPane(businessImage);
        mainFrame.addBusinessIcon(iconButton);

        /////////////////////////////////////////////function
        Image normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn_normal.png");
        Image hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn2_down.png");
        Image pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/menu_btn_highlight.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainFrame.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/tools_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainFrame.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_highlight.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/message_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainFrame.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/filemanager_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainFrame.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/mycollection_mainpanel.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/myCollection_mainpanel_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/myCollection_mainpanel_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainFrame.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/find_down.png");

        iconButton = new IconPane("查找", normalImage, hoverImage, pressedImage);
        mainFrame.addFunctionIcon(iconButton);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/store.png");

        iconButton = new IconPane("应用宝", normalImage);
        mainFrame.addRightFunctionIcon(iconButton);

        /////////////////////////////////////////////////////////////////////app
        normalImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn_hover.png");
        pressedImage = ImageBox.getImageClassPath("/resources/main/images/bottom/appbox_mgr_btn_down.png");

        iconButton = new IconPane(normalImage, hoverImage, pressedImage);
        mainFrame.addRightAppIcon(iconButton);

        Image appImage = ImageBox.getImageClassPath("/resources/main/images/app/1.png");

        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/2.png");

        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/3.png");

        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/7.png");
        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/8.png");
        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/9.png");
        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/10.png");
        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

        appImage = ImageBox.getImageClassPath("/resources/main/images/app/11.png");
        iconButton = new IconPane(appImage);
        mainFrame.addAppIcon(iconButton);

    }

    private void initUserList() {
        Random random = new Random();

        Image normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_normal.png");
        Image hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_hover.png");
        Image selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_selected.png");
        ListRootPanel userList = new ListRootPanel();
        mainFrame.addTab(normalImage, hoverImage, selectedImage, userList);

        ListNodePanel[] teamNode = new ListNodePanel[5];
        for (int j = 0; j < 5; j++) {
            teamNode[j] = new ListNodePanel();
            teamNode[j].setText("我的好友" + j);

            for (int i = 0; i < 5; i++) {
                int index = random.nextInt(100) + 1;
                SimpleHead head = new SimpleHead();
                head.setHeadSize(20);
                Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + index + ".png", 40, 40);
                head.setImage(image);
                head.setText("女神经" + (j + 1));

                head.setOnMouseClicked(m->{
                	if (m.getClickCount() == 2) {
        			}
                });
                teamNode[j].addItem(head);
            }
            userList.addNode(teamNode[j]);
        }

        normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_normal.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_hover.png");
        selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_selected.png");

        ListRootPanel groupRoot = new ListRootPanel();
        mainFrame.addTab(normalImage, hoverImage, selectedImage, groupRoot);

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
        normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_normal.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_hover.png");
        selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/icon_last_selected.png");

        ListRootPanel lastRoot = new ListRootPanel();
        mainFrame.addTab(normalImage, hoverImage, selectedImage, lastRoot);

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
//
        normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/qzone_normal.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/qzone_hover.png");
        selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/qzone_selected.png");

        VBox boxq = new VBox();

//        webEngine.load("http://www.qq.com");
//        boxq.getChildren().add(webView);
        boxq.getChildren().add(new Button("我的空间"));
        boxq.setStyle("-fx-background-color:rgba(44, 123, 245, 1)");
        mainFrame.addTab(normalImage, hoverImage, selectedImage, boxq);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_tab_inco_normal.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_tab_inco_hover.png");
        selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_tab_inco_selected.png");

        VBox box4 = new VBox();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://www.oschina.net/code/snippet_935786_52805");
        box4.getChildren().add(webView);
        box4.setStyle("-fx-background-color:rgba(215, 165, 230, 1)");
        mainFrame.addTab(normalImage, hoverImage, selectedImage, box4);

        normalImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_phone_inco_normal.png");
        hoverImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_phone_inco_hover.png");
        selectedImage = ImageBox.getImageClassPath("/resources/main/images/panel/main_panel_phone_inco_selected.png");

        VBox box5 = new VBox();
//        webView = new WebView();
//        webEngine = webView.getEngine();
//        webEngine.load("http://download.csdn.net/detail/onlyxiahui/9434701");
        box5.getChildren().add(new Button("我的手机"));
        box5.setStyle("-fx-background-color:rgba(112, 245, 86, 1);");
        mainFrame.addTab(normalImage, hoverImage, selectedImage, box5);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
