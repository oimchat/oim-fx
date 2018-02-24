package com.onlyxiahui.im.message.data.setting;

/**
 * @Author: XiaHui
 * @Date: 2016年1月5日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2016年1月5日
 */
public class ChatSetting {

	private long fileLimitSize = (1024 * 1024);
	private String fileLimitInfo = "测试版本，暂不支持大于1MB文件";

	private long imageLimitSize = (1024 * 500);
	private String imageLimitInfo = "测试版本，暂不支持大于500KB图片";

	public long getFileLimitSize() {
		return fileLimitSize;
	}

	public void setFileLimitSize(long fileLimitSize) {
		this.fileLimitSize = fileLimitSize;
	}

	public String getFileLimitInfo() {
		return fileLimitInfo;
	}

	public void setFileLimitInfo(String fileLimitInfo) {
		this.fileLimitInfo = fileLimitInfo;
	}

	public long getImageLimitSize() {
		return imageLimitSize;
	}

	public void setImageLimitSize(long imageLimitSize) {
		this.imageLimitSize = imageLimitSize;
	}

	public String getImageLimitInfo() {
		return imageLimitInfo;
	}

	public void setImageLimitInfo(String imageLimitInfo) {
		this.imageLimitInfo = imageLimitInfo;
	}

}
