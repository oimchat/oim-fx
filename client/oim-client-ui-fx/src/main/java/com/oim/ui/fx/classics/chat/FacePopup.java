package com.oim.ui.fx.classics.chat;

import java.util.List;

import com.oim.fx.common.component.IconButton;
import com.only.fx.OnlyPopup;

import javafx.scene.Node;
import javafx.stage.Window;

/**
 * @author: XiaHui
 * @date: 2017年8月21日 下午4:30:21
 */
public class FacePopup {

	FacePane fp = new FacePane();
	OnlyPopup po = new OnlyPopup();

//	Set<EventAction<FaceData>> set = new HashSet<EventAction<FaceData>>();
//	EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
//
//		@Override
//		public void handle(ActionEvent event) {
//			// TODO 自动生成的方法存根
//
//		}
//	};
//	EventAction<FaceData> eventAction;

	public FacePopup() {
		po.setArrowLocation(OnlyPopup.ArrowLocation.BOTTOM_CENTER);
		po.setContentNode(fp);
		po.setArrowSize(0);
	}

	public final void show(Window ownerWindow, double anchorX, double anchorY) {
		po.show(ownerWindow, anchorX, anchorY);
	}

	public final void show(Node owner, double x, double y) {
		po.show(owner, x, y);
	}

	public final void show(Node owner) {
		po.show(owner);
	}

	public void set(String key, String name, List<IconButton> list) {
		fp.set(key, name, list);
	}

	public void hide(){
		po.hide();
	}
//	public void setAction(EventAction<FaceData> a) {
//		this.eventAction = a;
//		// set.add(a);
//	}
//
//	private void handle(String key, FaceData FaceData) {
//		if (null != eventAction) {
//			eventAction.execute(FaceData);
//		}
//		// for (EventAction<FaceData> ea : set) {
//		// ea.execute(FaceData);
//		// }
//	}
}
