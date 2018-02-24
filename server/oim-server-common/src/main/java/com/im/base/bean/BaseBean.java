package com.im.base.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Author: XiaHui
 * @Date: 2015年12月11日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2015年12月11日
 */
@MappedSuperclass
public class BaseBean {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 40)
	private String id;// 菜单编号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
