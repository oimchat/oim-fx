package com.onlyxiahui.im.bean;



/**
 * 描述：好友分组信息
 * 
 * @author XiaHui
 * @date 2014-06-15 2:32:18
 * @version 0.0.1
 */
public class UserCategory implements Category{

	private String id;
	private String userId;//拥有分组用户id
	private int rank;
	private int sort;
	private String name;

	public static final int sort_default = 1;
	public static final int sort_custom = 2;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
