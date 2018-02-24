package com.oim.ui.fx.classics;

import java.io.File;
import java.io.FileWriter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author XiaHui
 * @date 2017年6月1日 上午11:17:06
 */
public class ShowAccountDialog extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();
	TextArea textArea = new TextArea();

	private FileChooser fileChooser;

	public ShowAccountDialog(Stage owner) {
		this.initModality(Modality.APPLICATION_MODAL);
		this.initOwner(owner);// Set the owner of the Stage
		initComponents();
		initEvent();
	}

	private void initComponents() {
		this.setWidth(320);
		this.setHeight(240);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setTitle("账号信息");

		cancelButton.setText("关闭");
		cancelButton.setPrefWidth(80);

		button.setText("保存");
		button.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(button);
		bottomBox.getChildren().add(cancelButton);

		textArea.setEditable(false);
		textArea.setWrapText(true);

		rootPane.setTop(topBox);
		rootPane.setCenter(textArea);
		rootPane.setBottom(bottomBox);

		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("文本文件", "*.txt"));

	}

	private void initEvent() {
		button.setOnAction(a -> {
			save();
		});
		cancelButton.setOnAction(a -> {
			ShowAccountDialog.this.hide();
		});
	}

	public void setText(String text) {
		textArea.setText(text);
	}

	private void save() {
		fileChooser.setInitialFileName("账号信息.txt");
		File file = fileChooser.showSaveDialog(this.getScene().getWindow());
		if (null != file) {
			String fullPath = file.getAbsolutePath();
			writeFile(fullPath);
		}
	}

	public void writeFile(String fileName) {
		try {
			File f = new File(fileName);
			FileWriter write = new FileWriter(f);
			String text = textArea.getText();
			write.write(text);
			write.close();
		} catch (Exception e) {
			this.showPrompt("保存失败");
		}
	}
}
