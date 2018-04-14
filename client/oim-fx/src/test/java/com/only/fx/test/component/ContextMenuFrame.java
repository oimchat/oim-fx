/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.test.component;

import com.oim.fx.ui.*;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.BaseStage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * 登录窗口
 *
 * @author XiaHui
 */
public class ContextMenuFrame extends BaseStage {

    private StackPane centerPane = new StackPane();

    Image onlineImage = ImageBox.getImageClassPath("/resources/common/images/status/online.png");
    Image callMeImage = ImageBox.getImageClassPath("/resources/common/images/status/call_me.png");
    Image awayImage = ImageBox.getImageClassPath("/resources/common/images/status/away.png");
    Image busyImage = ImageBox.getImageClassPath("/resources/common/images/status/busy.png");
    Image muteImage = ImageBox.getImageClassPath("/resources/common/images/status/mute.png");
    Image invisibleImage = ImageBox.getImageClassPath("/resources/common/images/status/invisible.png");

    ContextMenu menu = new ContextMenu();

    public ContextMenuFrame() {
        initComponent();
        initContextMenu();
        iniEvent();
        initSet();
    }

    /**
     * 初始化界面各组件
     *
     * @author: XiaHui
     * @createDate: 2017年5月25日 下午5:56:15
     * @update: XiaHui
     * @updateDate: 2017年5月25日 下午5:56:15
     */
    private void initComponent() {
        this.setTitle("登录");
        this.setResizable(false);
        this.setWidth(445);
        this.setHeight(345);
        this.setTitlePaneStyle(2);
        this.setRadius(5);
        this.setCenter(centerPane);

    }

    /**
     * 初始化各事件
     *
     * @author: XiaHui
     * @createDate: 2017年5月25日 下午5:56:45
     * @update: XiaHui
     * @updateDate: 2017年5月25日 下午5:56:45
     */
    private void iniEvent() {
        centerPane.setOnContextMenuRequested(e -> {
            menu.show(ContextMenuFrame.this, e.getX()+ContextMenuFrame.this.getX(), e.getY()+ContextMenuFrame.this.getY());
        });
    }

    private void initContextMenu() {
        //////////////////////////////////////

        ImageView iv = new ImageView();
        iv.setImage(onlineImage);
        MenuItem menuItem = new MenuItem("我在线上");
        menu.getItems().add(menuItem);
        menuItem.setOnAction((ActionEvent event) -> {
        });

        iv = new ImageView();
        iv.setImage(callMeImage);
        menuItem = new MenuItem("Q我吧");
        menu.getItems().add(menuItem);
        menuItem.setOnAction((ActionEvent event) -> {
        });

        iv = new ImageView();
        iv.setImage(awayImage);
        menuItem = new MenuItem("离开");
        menu.getItems().add(menuItem);
        menuItem.setOnAction((ActionEvent event) -> {
        });

        iv = new ImageView();
        iv.setImage(busyImage);
        menuItem = new MenuItem("忙碌");
        menu.getItems().add(menuItem);
        menuItem.setOnAction((ActionEvent event) -> {
        });

        iv = new ImageView();
        iv.setImage(muteImage);
        menuItem = new MenuItem("请勿打扰");
        menu.getItems().add(menuItem);
        menuItem.setOnAction((ActionEvent event) -> {
        });

        iv = new ImageView();
        iv.setImage(invisibleImage);
        menuItem = new MenuItem("隐身");
        menu.getItems().add(menuItem);
        menuItem.setOnAction((ActionEvent event) -> {
        });
    }

    private void initSet() {

    }
}
