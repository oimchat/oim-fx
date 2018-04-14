package com.oim.fx.ui.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.oim.fx.common.component.BaseStackPane;
import com.oim.fx.common.component.FreeStage;

import javafx.application.Platform;
import javafx.scene.paint.Color;

/**
 * @author XiaHui
 * @date 2017-09-28 2:04:59
 */
public class UIBox {
	static Color color;
	static String backgroundImageUrl;
	public static Set<FreeStage> baseFrameSet = Collections.synchronizedSet(new HashSet<FreeStage>());
	public static Set<BaseStackPane> basePaneSet = Collections.synchronizedSet(new HashSet<BaseStackPane>());
	
	public static void add(FreeStage baseFrame) {
		if (null != color) {
			baseFrame.setBackgroundColor(color);
		}
		if (null != backgroundImageUrl && !"".equals(backgroundImageUrl)) {
			baseFrame.setBackground(backgroundImageUrl);
		}
		baseFrameSet.add(baseFrame);
	}

	public static void setColor(Color color) {
		UIBox.color=color;
	}

	public static void setBackgroundImageUrl(String backgroundImageUrl) {
		UIBox.backgroundImageUrl=backgroundImageUrl;
	}

	public static void update() {
		for (FreeStage baseFrame : UIBox.baseFrameSet) {
			if (null != color) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						baseFrame.setBackgroundColor(color);
					}
				});

			}
			if (null != backgroundImageUrl && !"".equals(backgroundImageUrl)) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						baseFrame.setBackground(backgroundImageUrl);
					}
				});
			}
		}
	}
}
