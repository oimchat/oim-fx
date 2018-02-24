package com.oim.core.common.component.file;

/**
 * @author XiaHui
 * @date 2017年6月17日 下午9:10:27
 */
public class FileHandleInfo {
	
	private boolean success = true;
	private FileInfo fileInfo;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
}
