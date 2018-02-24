package com.im.server.general.common.bean;

import java.util.Date;

import javax.persistence.Entity;

import com.im.base.bean.BaseBean;

/**
 * @author: XiaHui
 * @date: 2017年8月17日 下午2:54:20
 */
@Entity(name = "im_text_notice")
public class TextNotice extends BaseBean {

	private String url;
	private String title;
	private String content;
	private long timestamp;
	private String openType;// 1:app 2：browser
	private String pushType;
	private Date createTime;// 建立时间

	public static String open_type_app = "1";
	public static String open_type_browser = "2";

	public static String push_type_all = "1";
	public static String push_type_part = "2";

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

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
