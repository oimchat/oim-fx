package com.oim.core.common.action;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月17日 下午12:23:03
 * @version 0.0.1
 */
public interface BackAction<T> {

	public void back(BackInfo backInfo, T t);
}
