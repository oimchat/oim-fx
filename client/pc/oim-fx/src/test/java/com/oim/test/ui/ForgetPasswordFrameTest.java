/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import java.util.ArrayList;
import java.util.List;

import com.oim.ui.fx.classics.ForgetPasswordFrame;
import com.oim.ui.fx.classics.forget.AccountPane;
import com.oim.ui.fx.classics.forget.QuestionItem;
import com.oim.ui.fx.classics.forget.QuestionPane;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class ForgetPasswordFrameTest extends Application {

	ForgetPasswordFrame l = new ForgetPasswordFrame();
	AccountPane ap = new AccountPane();
	QuestionPane qp=new QuestionPane();
	
	@Override
	public void start(Stage primaryStage) {
		l.show();
		l.setCenterNode(ap);

		ap.setOnNextAction(a -> {
			l.setCenterNode(qp);
		});
		
		qp.setOnUpAction(a -> {
			l.setCenterNode(ap);
		});
		
		List<QuestionItem> list=new ArrayList<QuestionItem>();
		
		for(int i=0;i<3;i++){
			QuestionItem qi=new QuestionItem();
			qi.setQuestion("你的父亲叫什么？");
			list.add(qi);
		}
		qp.setQuestionItemList(list);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
