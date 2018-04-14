package com.onlyxiahui.im.message.data;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午10:50:36
 */
public class PageData {

	private int pageSize = 30;// 页码大小
	private int totalCount = 0;
	private int pageNumber = 1;
	private int totalPage;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
