/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import java.util.ArrayList;
import java.util.List;

import com.oim.ui.fx.classics.forget.AccountPane;
import com.oim.ui.fx.classics.forget.QuestionItem;
import com.oim.ui.fx.classics.forget.QuestionPane;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class ForgetPasswordFrameTest extends Application {

	ForgetPasswordFrame stage = new ForgetPasswordFrame();
	AccountPane ap = new AccountPane();
	QuestionPane qp = new QuestionPane();
	List<QuestionItem> itemList = new ArrayList<QuestionItem>();

	@Override
	public void start(Stage primaryStage) {
		stage.show();
		initEvent();
		stage.setCenterNode(ap);
	}

	private void initEvent() {

		ap.setOnNextAction(a -> {
			qp.setDoneButtonVisible(false);
			itemList.clear();
			for (int i = 0; i < 8; i++) {
				QuestionItem qi = new QuestionItem();
				qi.setQuestion("哈哈哈");
				qi.setQuestionId("" + i);
				itemList.add(qi);
			}
			qp.setQuestionItemList(itemList);
			qp.setDoneButtonVisible(true);
			stage.setCenterNode(qp);
		});

		qp.setOnUpAction(a -> {
			stage.setCenterNode(ap);
		});

		qp.setOnDoneAction(a -> {

		});

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
