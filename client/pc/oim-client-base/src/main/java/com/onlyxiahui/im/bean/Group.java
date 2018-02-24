package com.onlyxiahui.im.bean;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014-06-15 02:30:04
 * @version 0.0.1
 */
public class Group {

	private String id;
	private long number;// 帐号
	private String name = "";
	private String head;// 照片
	private String remark = "";
	private String classification = "";
	private String publicNotice = "";
	private String introduce = "";
	private String position = "";
	private String remarkName;// 备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

}
