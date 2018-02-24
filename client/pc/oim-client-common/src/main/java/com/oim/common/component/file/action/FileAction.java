package com.oim.common.component.file.action;

/**
 * @author XiaHui
 * @date 2017年5月31日 下午8:14:00
 */
public interface FileAction<T> {

	void progress(long speed, long size, long finishSize, double progress);

	void success(T t);

	void lost(T t);
}
