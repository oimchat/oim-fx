/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.test;

import java.io.File;

import com.oim.fx.common.component.BaseStage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author XiaHui
 */
public class WebFrame extends BaseStage {
    
    public WebFrame() {
        init();
    }
    
    private void init() {
        
        VBox rootVBox = new VBox();
        
        this.setCenter(rootVBox);
       // this.setBackground("Resources/Images/Wallpaper/1.jpg");
        this.setTitle("登录");
        this.setWidth(380);
        this.setHeight(600);
        this.setRadius(10);
        
        StackPane u = new StackPane();
        u.setPrefSize(50, 100);
       // String hString = this.getClass().getResource("/resources/chat/index.html").toString();
        File file = new File("Resources/Images/Head/User/90_100.gif");
        
        String fullPath = file.getAbsolutePath();
        String htmlText = "<html><body> <lable>666</label>"
                + "<img src=\"file:/"+fullPath+"\" />"
                + "</body></html> ";
        System.out.println(htmlText);
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        WebEngine we = webView.getEngine();
        //we.load(hString);
        Image logoIamge = new Image(this.getClass().getResource("/resources/login/logo.png").toExternalForm(), true);
        ImageView logoImageView = new ImageView();
        logoImageView.setImage(logoIamge);
        we.loadContent(htmlText);
       // Document d = we.getDocument();
        // Element root = we.getDocument().getDocumentElement();
        // NodeList nl = root.getElementsByTagName("body");
        // Node node = nl.item(0);

        //node.appendChild(node);
        rootVBox.getChildren().add(u);
        rootVBox.getChildren().add(webView);
    }
}
