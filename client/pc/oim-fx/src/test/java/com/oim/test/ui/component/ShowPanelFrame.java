/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.html.HTMLElement;

import com.oim.fx.common.component.BaseStage;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 *
 * @author Only
 */
public class ShowPanelFrame extends BaseStage {
	VBox box = new VBox();
	WebView webView = new WebView();
	Button button = new Button("插入");
	WebPage webPage;

	public ShowPanelFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("登录");
		this.setWidth(440);
		this.setHeight(360);
		this.setCenter(box);

		box.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");

		box.getChildren().add(button);
		box.getChildren().add(webView);

		File file = new File("Resources/Images/Head/User/90_100.gif");
		//
		String fullPath = file.getAbsolutePath();
		String htmlText = "<html><body> <lable id=\"show_text\">666</label>" + "<img src=\"file:/" + fullPath + "\" />"
				+ "</body></html> ";

		// WebPage webPage=new WebPage();
		// WebEngine we = webView.getEngine();
		// // we.loadContent(htmlText);
		webPage = Accessor.getPageFor(webView.getEngine());
		webPage.setEditable(false);
		webPage.load(webPage.getMainFrame(), htmlText, "text/html");
		//webPage.setUserAgent(userAgent);
		//webView.
		//webView.setContextMenuEnabled(false);
		webPage.setLocalStorageEnabled(false);
		webView.setOnContextMenuRequested(new  EventHandler<Event>(){

			@Override
			public void handle(Event event) {
				Object o=event.getSource();
				System.out.println(o.getClass());
			}});

	}

	private void initEvent() {
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				Document doc = webPage.getDocument(webPage.getMainFrame());
				if (doc instanceof HTMLDocument) {
					HTMLDocument htmlDocument = (HTMLDocument) doc;
					Element e = htmlDocument.createElement("div");
					e.setTextContent("hhhhhhh");
					HTMLElement htmlDocumentElement = (HTMLElement)htmlDocument.getDocumentElement();
			        HTMLElement htmlBodyElement = (HTMLElement)htmlDocumentElement.getElementsByTagName("body").item(0);
			        
					//e.setAttribute(name, value);
			        //org.w3c.dom.Node n=htmlBodyElement.getLastChild();;
			        htmlBodyElement.appendChild(e);
			        webPage.getClientInsertPositionOffset();
			       // htmlBodyElement.ge
					//hd.appendChild(e);
					System.out.println(e.getClass());
				}
			}
		});

	}

}
