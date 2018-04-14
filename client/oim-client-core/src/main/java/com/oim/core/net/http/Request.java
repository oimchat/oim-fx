package com.oim.core.net.http;

public class Request {

	private RequestMessage dataMap = new RequestMessage();
	private String controller;
	private String method;

	public Request() {

	}

	public Request(String controller, String method) {
		this.controller = controller;
		this.method = method;
	}

	public RequestMessage getRequestMessage() {
		return dataMap;
	}

	public void setRequestMessage(RequestMessage dataMap) {
		this.dataMap = dataMap;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void put(String key, Object value) {
		dataMap.put(key, value);
	}
}
