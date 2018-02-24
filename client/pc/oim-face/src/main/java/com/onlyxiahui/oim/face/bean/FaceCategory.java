package com.onlyxiahui.oim.face.bean;

import java.util.List;

/**
 * 表情分组信息
 * 
 * @author: XiaHui
 * @date: 2017-10-16 11:48:46
 */
public class FaceCategory {

	/** 分组id **/
	private String id;
	/** 分组name **/
	private String name;
	private List<FaceInfo> faceInfoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FaceInfo> getFaceInfoList() {
		return faceInfoList;
	}

	public void setFaceInfoList(List<FaceInfo> faceInfoList) {
		this.faceInfoList = faceInfoList;
	}
}
