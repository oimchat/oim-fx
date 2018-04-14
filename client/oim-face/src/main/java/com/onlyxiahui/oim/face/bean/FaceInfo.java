package com.onlyxiahui.oim.face.bean;

/**
 * 表情的图文信息
 * @author: XiaHui
 * @date: 2017-08-21 3:19:01
 */
public class FaceInfo {

	
	private String categoryId;
	private String key;
	private String realPath;
	private String showPath;
	private String text;
	private double width;
	private double height;

	private double imageWidth;
	private double imageHeight;

	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getShowPath() {
		return showPath;
	}

	public void setShowPath(String showPath) {
		this.showPath = showPath;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(double imageWidth) {
		this.imageWidth = imageWidth;
	}

	public double getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(double imageHeight) {
		this.imageHeight = imageHeight;
	}

}
