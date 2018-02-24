package com.oim.core.common.component.file;

import java.io.File;

/**
 * @author XiaHui
 * @date 2017年6月17日 下午9:10:27
 */
public class FileInfo {

	private String id;
	private File file;

	public String getName() {
		return (null == file) ? null : file.getName();
	}

	public String getAbsolutePath() {
		return (null == file) ? null : file.getAbsolutePath();
	}

	public long getSize() {
		return (null == file) ? 0 : file.length();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
