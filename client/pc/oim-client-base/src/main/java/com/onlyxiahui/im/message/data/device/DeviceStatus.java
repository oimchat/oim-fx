package com.onlyxiahui.im.message.data.device;

public class DeviceStatus {

	private int cpuCount;
	private int cpuUseRate;
	private int memorySize;
	private int memoryUseRate;
	private int diskSize;
	private int diskUseRate;
	private int networkError;
	private String address;
	private String mac;

	public int getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public int getCpuUseRate() {
		return cpuUseRate;
	}

	public void setCpuUseRate(int cpuUseRate) {
		this.cpuUseRate = cpuUseRate;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	public int getMemoryUseRate() {
		return memoryUseRate;
	}

	public void setMemoryUseRate(int memoryUseRate) {
		this.memoryUseRate = memoryUseRate;
	}

	public int getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}

	public int getDiskUseRate() {
		return diskUseRate;
	}

	public void setDiskUseRate(int diskUseRate) {
		this.diskUseRate = diskUseRate;
	}

	public int getNetworkError() {
		return networkError;
	}

	public void setNetworkError(int networkError) {
		this.networkError = networkError;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
