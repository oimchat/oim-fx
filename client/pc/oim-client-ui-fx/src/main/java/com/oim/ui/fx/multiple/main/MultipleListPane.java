package com.oim.ui.fx.multiple.main;

import com.oim.fx.ui.list.ListRootPanel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-12-13 11:59:43
 */
public class MultipleListPane extends BorderPane {

	private final ImageView logoImageView = new ImageView();
	private final Label textLabel = new Label();
	private final ListRootPanel listRooPane = new ListRootPanel();
	private final Button loginButton = new Button();

	public MultipleListPane() {
		initComponent();
		initEvent();
		initSet();
	}

	private void initComponent() {

		StackPane logoStackPane = new StackPane();
		logoStackPane.setPadding(new Insets(20, 0, 0, 0));
		logoStackPane.getChildren().add(logoImageView);

		HBox lineHBox = new HBox();
		lineHBox.setMinHeight(1);
		lineHBox.setStyle("-fx-background-color:#ed3a3a;");

		textLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1);-fx-font-size: 16px;");

		HBox textLabelHBox = new HBox();
		textLabelHBox.setAlignment(Pos.CENTER);
		textLabelHBox.getChildren().add(textLabel);

		loginButton.setText("登录微信");
		loginButton.setPrefSize(220, 35);

		HBox loginButtonHBox = new HBox();
		loginButtonHBox.setPadding(new Insets(0, 4, 10, 4));
		loginButtonHBox.setAlignment(Pos.CENTER);
		loginButtonHBox.getChildren().add(loginButton);

		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setStyle("-fx-background-color:#26292e;");
		vBox.getChildren().add(logoStackPane);
		vBox.getChildren().add(lineHBox);
		vBox.getChildren().add(textLabelHBox);
		vBox.getChildren().add(loginButtonHBox);

		this.setTop(vBox);
		this.setCenter(listRooPane);
		this.setStyle("-fx-background-color:#2e3238;");
	}

	private void initEvent() {
		listRooPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			e.consume();
		});
	}

	private void initSet() {
		// TODO Auto-generated method stub

	}

	public void setLogoImage(Image value) {
		this.logoImageView.setImage(value);
	}

	public void setText(String value) {
		this.textLabel.setText(value);
	}

	public ListRootPanel getListRooPane() {
		return listRooPane;
	}

	public void setOnLoginAction(EventHandler<ActionEvent> value) {
		loginButton.setOnAction(value);
	}
}
