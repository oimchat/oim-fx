package com.only.fx.common.action;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-25 09:43:12
 */
public interface ExecuteAction {

	public <T, E> E execute(T value);
}
