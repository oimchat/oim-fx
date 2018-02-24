package com.im.server.general.common.data;

/**
 * @author: XiaHui
 * @date: 2016年8月24日 上午10:12:59
 */
public class ChatItem {

	private String type;//
	private String value;//
	private String filterValue;// 过滤后内容
	private int rank;// 排序
	private int section;// 段落

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}
}
