package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

/**
 * 描述：好友分组信息
 * 
 * @author XiaHui
 * @date 2014年6月15日 下午2:32:18
 * @version 0.0.1
 */
@Entity(name = "im_user_category")
public class UserCategory extends BaseBean{

	private String userId;//拥有分组用户id
	private int rank;
	private int sort;
	private String name;

	public static final int sort_default = 1;
	public static final int sort_custom = 2;

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
