package com.oim.common.event;
/**
 * 描述：
 * @author XiaHui 
 * @date 2015年3月24日 下午10:02:32
 * @version 0.0.1
 */
public interface ExecuteAction {

	public <T, E> E execute(T value);
}
