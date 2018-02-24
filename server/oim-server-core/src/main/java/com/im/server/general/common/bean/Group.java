package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

/**
 * 描述：群信息实体
 * 
 * @author XiaHui
 * @date 2014年6月15日 下午2:30:04
 * @version 0.0.1
 */
@Entity(name = "im_group")
public class Group extends BaseBean{

	@Column(unique = true, nullable = false)
	private long number;// 帐号

	private String name = "";
	private String head;// 照片
	private String remark = "";
	private String classification = "";// 分类
	@Type(type = "text")
	private String publicNotice = "";// 公告
	@Type(type = "text")
	private String introduce = "";// 介绍
	private String position = "";
	private Date createTime;// 建立时间


	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getPublicNotice() {
		return publicNotice;
	}

	public void setPublicNotice(String publicNotice) {
		this.publicNotice = publicNotice;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
