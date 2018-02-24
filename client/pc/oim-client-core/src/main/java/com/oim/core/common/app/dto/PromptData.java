package com.oim.core.common.app.dto;

import com.oim.core.common.action.CallAction;

/**
 * @Author: XiaHui
 * @Date: 2016年1月23日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2016年1月23日
 */
public class PromptData {

	private IconType iconType;
	private String icon;
	private String key;
	private CallAction executeAction;

	public PromptData(CallAction executeAction) {
		this.executeAction = executeAction;
	}

	public PromptData(IconType iconType, String icon, CallAction executeAction) {
		this.iconType = iconType;
		this.icon = icon;
		this.executeAction = executeAction;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CallAction getExecuteAction() {
		return executeAction;
	}

	public IconType getIconType() {
		return iconType;
	}

	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}

	public void setExecuteAction(CallAction executeAction) {
		this.executeAction = executeAction;
	}

	public enum IconType {

		userHead, groupHead, classImage, pathImage, system, prompt
	}
}
