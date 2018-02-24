package com.oim.server.api.tools.ui;

import com.oim.server.api.tools.ui.main.AddressPane;
import com.oim.server.api.tools.ui.main.RequestPane;
import com.oim.server.api.tools.ui.main.ShowPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author: XiaHui
 * @date: 2018-02-22 11:22:50
 */
public class MainStage extends CommonStage {

	private BorderPane baseBorderPane = new BorderPane();

	private SplitPane splitPane = new SplitPane();

	private BorderPane connectBorderPane = new BorderPane();
	private AddressPane addressPane = new AddressPane();
	private Button connectButton = new Button();

	private RequestPane requestPane = new RequestPane();
	private BorderPane receiveBorderPane = new BorderPane();
	// private TextArea receiveTextArea = new TextArea();
	private Label receiveLabel = new Label();
	private ShowPane receiveShowPane = new ShowPane();
	private HBox bottomBox = new HBox();
	private Button sendButton = new Button();
	private Button exitButton = new Button();

	public MainStage() {
		initComponent();
	}

	private void initComponent() {
		this.setTitle("OIM聊天服务接口调试工具");
		this.setCenter(baseBorderPane);
		this.setWidth(950);
		this.setHeight(650);
		this.setMinWidth(350);
		this.setMinHeight(380);
		this.setBackground(MainStage.class.getResource("/images/wallpaper/default.jpg"));

		connectButton.setText("连接");
		connectButton.setPrefWidth(80);
		StackPane connectStackPane = new StackPane();

		connectStackPane.getChildren().add(connectButton);
		
		connectBorderPane.setPadding(new Insets(10, 0, 0, 0));
		connectBorderPane.setLeft(addressPane);
		connectBorderPane.setCenter(connectStackPane);
		// connectBorderPane.setRight(connectStackPane);
		connectBorderPane.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");

		receiveLabel.setText("服务器推送内容：");
		
		receiveBorderPane.setTop(receiveLabel);
		receiveBorderPane.setCenter(receiveShowPane);

		splitPane.getItems().add(requestPane);
		splitPane.getItems().add(receiveBorderPane);
		splitPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");

		splitPane.setDividerPositions(0.5f, 0.5f);
		splitPane.setOrientation(Orientation.VERTICAL);

		exitButton.setText("退出");
		exitButton.setPrefWidth(80);

		sendButton.setText("发送");
		sendButton.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(exitButton);
		bottomBox.getChildren().add(sendButton);

		baseBorderPane.setTop(connectBorderPane);
		baseBorderPane.setCenter(splitPane);
		baseBorderPane.setBottom(bottomBox);
	}

	public void setOnConnect(EventHandler<ActionEvent> value) {
		connectButton.setOnAction(value);
	}

	public void setOnSend(EventHandler<ActionEvent> value) {
		sendButton.setOnAction(value);
	}

	public void setOnExit(EventHandler<ActionEvent> value) {
		exitButton.setOnAction(value);
	}

	public void setConnectButtonDisable(boolean value) {
		connectButton.setDisable(value);
	}

	public boolean addressVerify() {
		return addressPane.verify();
	}

	public String getAddress() {
		return addressPane.getAddress();
	}

	public int getPort() {
		return addressPane.getPort();
	}

	public String getRequestText() {
		return requestPane.getRequestText();
	}

	public void appendReceiveText(String receiveText) {

		// StringBuilder sb = new StringBuilder();
		// sb.append("<pre>");
		// sb.append(" <code class=\"html\">");
		// sb.append(receiveText);
		// sb.append(" </code>");
		// sb.append("</pre>");
		// receiveShowPane.insertLastHtml(sb.toString());

		receiveShowPane.insertLastJsonViewer(receiveText);
		receiveShowPane.checkElement();
	}

	public void setBackText(String text) {
		requestPane.setBackText(text);
	}
}
