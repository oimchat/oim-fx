/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.AddFrame;
import com.oim.ui.fx.classics.FindFrame;
import com.oim.ui.fx.classics.add.InfoPane;
import com.oim.ui.fx.classics.find.FindGroupItem;
import com.oim.ui.fx.classics.find.FindGroupPane;
import com.oim.ui.fx.classics.find.FindUserItem;
import com.oim.ui.fx.classics.find.FindUserPane;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Only
 */
public class FindFrameTest extends Application {
	
	AddFrame af=new AddFrame();
	
	FindFrame ff = new FindFrame();
	FindUserPane fup;
	FindGroupPane fgp;
	int size = 10;

	@Override
	public void start(Stage primaryStage) {
		ff.show();

		fup = ff.getFindUserPane();
		fgp = ff.getFindGroupPane();

		fup.setSearchAction(a -> {
			System.out.println(fup.getGender());
		});

		fup.getQueryListPane().setPage(0, 10);
		fup.getQueryListPane().setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {
				System.out.println(index);
				getUserList(index + 1);
				return new Label("第" + (index + 1) + "页");
			}
		});

		fgp.getQueryListPane().setPage(0, 10);
		fgp.getQueryListPane().setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {
				System.out.println(index);
				getGroupList(index + 1);
				return new Label("第" + (index + 1) + "页");
			}
		});
		
		af.addCategory("100", "我的好友");
		af.addCategory("101", "444好友");
		af.addCategory("102", "154");
		af.selectCategory(0);
	}

	Random random = new Random();

	public void getUserList(int index) {
		List<FindUserItem> list = new ArrayList<FindUserItem>();
		for (int i = ((index - 1) * size); i < index * size; i++) {
			int n = random.nextInt(100) + 1;
			
			
			
			
			String nickname="哈加额这事一个有故事的昵称";
			StringBuilder sb=new StringBuilder();
			sb.append("21岁|");
			sb.append("湖南 长沙\n");
			
			StringBuilder showText=new StringBuilder();
			showText.append("性别：男\n");
			showText.append("生日：2011-06-12\n");
			
			FindUserItem head = new FindUserItem();
			Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + n + "_100.gif", 100, 100);
			head.setHeadImage(image);
			head.setNickname(nickname);
			head.setShowText(sb.toString());
			head.setMaxWidth(200);

			Map<String,String> map=new HashMap<String,String>();
			map.put("hean", n+"");
			map.put("name", nickname);
			map.put("text", sb.toString());
			map.put("showText", showText.toString());
			
			head.addAttribute("user", map);
			head.setAddAction(a->{
				af.show();
				
				InfoPane ip=af.getInfoPanel();
		    	ip.setHeadImage(image);
				ip.setName(nickname);
				ip.setNumber("10089");
				ip.setInfoText(sb.toString()+showText.toString());
			});
			list.add(head);
		}
		fup.getQueryListPane().setNodeList(list);
	}

	public void getGroupList(int index) {
		List<FindGroupItem> list = new ArrayList<FindGroupItem>();
		for (int i = ((index - 1) * size); i < index * size; i++) {
			String name="哈加额这事d挑的群";
			StringBuilder sb=new StringBuilder();
			sb.append("21/500");
			
			StringBuilder showText=new StringBuilder();
			showText.append("好多人啊是，都在啊嘎巴那你\n");
			showText.append("发啊是撒娇啊是绝对\n");
			
			
			int n = random.nextInt(2) + 1;
			FindGroupItem head = new FindGroupItem();
			Image image = ImageBox.getImagePath("Resources/Images/Head/Group/" + n + ".png", 65, 65);
			head.setHeadImage(image);
			head.setName(name);
			head.setShowText(sb.toString());
			head.setInfoText(showText.toString());
			
			head.setPrefSize(275, 160);
			head.setMaxWidth(275);
			head.setMaxHeight(160);
			
			head.setAddAction(a->{
				af.show();
				
				InfoPane ip=af.getInfoPanel();
		    	ip.setHeadImage(image);
				ip.setName(name);
				ip.setNumber("10089");
				ip.setInfoText(showText.toString()+showText.toString());
			});
			list.add(head);
		}
		fgp.getQueryListPane().setNodeList(list);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
