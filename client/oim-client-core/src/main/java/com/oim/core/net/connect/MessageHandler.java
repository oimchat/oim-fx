package com.oim.core.net.connect;

/**
 * @Author: XiaHui
 * @Date: 2016年1月4日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2016年1月4日
 */
public interface MessageHandler {

	public void setBackTime(long time);

	public void back(Object data);

	public void addExceptionData(String key);
}
