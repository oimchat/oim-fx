package com.onlyxiahui.im.bean;



/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月15日 下午2:32:18
 * @version 0.0.1
 */
public class GroupCategory {


	private String id;// 
	private String userId;// 
	private int rank;
	private int sort;
	private String name;

	public static final int sort_default = 1;
	public static final int sort_custom = 2;

	public String getId() {
		return id;
	}

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
