package com.im.server.general.file.bean;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.im.base.bean.BaseBean;

/**
 * @author XiaHui
 * @date 2017-11-24 22:35:51
 */
@MappedSuperclass
public class FileBaseData extends BaseBean {

	private String name;// 不包含后缀的原始文件名(123)
	private String saveName;// 不包含后缀的保存在服务器的文件名(123)
	private String suffixName;// 后缀名 (.jpg)
	private String rootPath;// 根目录(D:/)
	private String nodePath;// 根目录以后的子目录(upload/)
	private String type;
	private long size;
	private String md5;
	private String sha1;
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getSuffixName() {
		return suffixName;
	}

	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
