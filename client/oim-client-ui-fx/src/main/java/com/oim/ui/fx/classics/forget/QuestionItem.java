package com.oim.ui.fx.classics.forget;

import com.only.fx.OnlyPopupOver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author: XiaHui
 * @date: 2017年6月5日 下午4:20:59
 */
public class QuestionItem extends VBox {

	TextArea questionTextArea = new TextArea();
	TextField answerField = new TextField();
	OnlyPopupOver over = new OnlyPopupOver();// 昵称提示
	Label overLabel = new Label("答案不能为空！");
	String questionId;

	public QuestionItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setPadding(new Insets(5, 10, 5, 10));
		questionTextArea.setEditable(false);

		Label questionLabel = new Label("问题：");
		Label answerLabel = new Label("答案：");

		questionTextArea.setPrefSize(320, 50);
		answerField.setPrefSize(320, 25);
		
		HBox box1 = new HBox();
		box1.getChildren().add(questionLabel);
		box1.getChildren().add(questionTextArea);
		HBox box2 = new HBox();
		box2.getChildren().add(answerLabel);
		box2.getChildren().add(answerField);
		
		this.getChildren().add(box1);
		this.getChildren().add(box2);
		
		over.setContentNode(overLabel);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public boolean verify() {
		String answer = answerField.getText();
		boolean mark = true;
		if (null == answer || "".equals(answer)) {
			over.show(answerField);
			answerField.requestFocus();
			mark = false;
		} else {
			mark = true;
		}
		return mark;
	}

	public void setQuestion(String question) {
		this.questionTextArea.setText(question);
	}

	public String getAnswer() {
		return answerField.getText();
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

}
