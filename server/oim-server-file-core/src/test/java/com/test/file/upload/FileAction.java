package com.test.file.upload;

/**
 * @author XiaHui
 * @date 2017-05-31 08:14:00
 */
public interface FileAction<T> {

	void progress(long speed, long size, long finishSize, double progress);

	void done(T t);
	
	void lost();
}
