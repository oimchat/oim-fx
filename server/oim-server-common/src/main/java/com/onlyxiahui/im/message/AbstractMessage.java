package com.onlyxiahui.im.message;

public abstract class AbstractMessage implements Data {
	
	public abstract Head getHead();

	public abstract void setHead(Head head);
}
