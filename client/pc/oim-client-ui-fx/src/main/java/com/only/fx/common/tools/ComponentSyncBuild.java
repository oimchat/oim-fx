package com.only.fx.common.tools;

import com.sun.javafx.tk.Toolkit;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ComponentSyncBuild {

	private final ComponentBlockingHandler componentBlockingHandler = new ComponentBlockingHandler();

	public <T extends Stage> T getStage(String key, SyncBuild<T> syncBuild) {
		return getComponent(key, syncBuild);
	}

	public <T extends Node> T getNode(String key, SyncBuild<T> syncBuild) {
		return getComponent(key, syncBuild);
	}

	public <T> T getComponent(String key, SyncBuild<T> syncBuild) {
		boolean isFxUserThread = Toolkit.getToolkit().isFxUserThread();
		if (isFxUserThread) {
			T t = syncBuild.build();
			return t;
		} else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					T t = syncBuild.build();
					componentBlockingHandler.put(key, t);
				}
			});
			T t = componentBlockingHandler.get(key);
			return t;
		}
	}

}
