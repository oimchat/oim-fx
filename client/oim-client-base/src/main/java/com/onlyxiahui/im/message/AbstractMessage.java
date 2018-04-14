package com.onlyxiahui.im.message;

public abstract class AbstractMessage implements Data {
	
	@Override
	public abstract Head getHead();

	@Override
	public abstract void setHead(Head head);
}
