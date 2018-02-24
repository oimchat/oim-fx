package com.im.server.general.common.data;

/**
 * @author: XiaHui
 * @date: 2016年8月24日 下午1:56:02
 */
public class BanChat {

	private long banTime;// 禁止时间长度
	private long startTime;// 开始禁止的时间
	private long endTime;// 到期时间

	public long getBanTime() {
		return banTime;
	}

	public void setBanTime(long banTime) {
		this.banTime = banTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
