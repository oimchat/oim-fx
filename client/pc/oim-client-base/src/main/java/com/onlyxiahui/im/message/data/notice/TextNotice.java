package com.onlyxiahui.im.message.data.notice;

/**
 * @author: XiaHui
 * @date: 2017年8月17日 下午2:54:20
 */
public class TextNotice {

	private String url;
	private String title;
	private String content;
	private String contentColor;
	private long timestamp;
	private String openType;// 1:app 2：browser
	private String createTime;// 建立时间

	public static String open_type_app = "1";
	public static String open_type_browser = "2";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentColor() {
		return contentColor;
	}

	public void setContentColor(String contentColor) {
		this.contentColor = contentColor;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
