package com.oim.ui.fx.classics;

import com.oim.ui.fx.classics.head.HeadListPane;
import com.only.fx.common.action.EventAction;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 * @author: XiaHui
 * @date: 2017年6月7日 下午5:46:58
 */
public class HeadChooseFrame extends CommonStage {
	
	HeadListPane p = new HeadListPane();
	Alert alert;

	public HeadChooseFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("选择头像");
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setWidth(430);
		this.setHeight(580);
		this.setCenter(p);
		
		alert = new Alert(AlertType.CONFIRMATION, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(this);
		alert.getDialogPane().setContentText("确定选择");
		alert.getDialogPane().setHeaderText(null);
		
		p.setPrefWrapLength(400);

	}

	private void iniEvent() {

	}

	public void select(EventAction<Boolean> a) {
		alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
			a.execute(true);
		});
	}

	public HeadListPane getHeadListPanel() {
		return p;
	}
}
