/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Only
 */
public class WebFrame extends CommonStage {
    
	WebView webView = new WebView();
    VBox vBox=new VBox();
    public WebFrame() {
    	initComponent();
		iniEvent();
    }
    
    private void initComponent() {
        this.setTitle("");
        this.setMinWidth(320);
        this.setMinHeight(240);
        
        this.setWidth(800);
        this.setHeight(480);
        
        
        VBox rootBox = new VBox();
		this.setCenter(rootBox);
		
		rootBox.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");
		rootBox.getChildren().add(webView);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub
		
	}
    
    public void load(String url){
    	WebEngine webEngine = webView.getEngine();
    	webEngine.load(url);
    }
}
