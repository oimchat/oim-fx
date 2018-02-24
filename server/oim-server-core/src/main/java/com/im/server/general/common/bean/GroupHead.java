package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import java.util.Date;

import javax.persistence.Entity;

/**
 * @author: XiaHui
 * @date: 2017年5月31日 下午3:40:50
 */
@Entity(name = "im_group_head")
public class GroupHead extends BaseBean {

	private String groupId;
	private String headId;
	private String fileName;
	private String type;
	private String url;
	private Date createTime;// 建立时间

	public static final String type_system = "1";
	public static final String type_custom = "2";

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
