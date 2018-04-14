package com.onlyxiahui.im.message.server;

import com.onlyxiahui.im.message.Head;

public class ServerHead extends Head{
	
	private String resultCode;
	private String resultMessage;
	
	public ServerHead() {
		resultCode = code_success;
		resultMessage = "";
	}
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
