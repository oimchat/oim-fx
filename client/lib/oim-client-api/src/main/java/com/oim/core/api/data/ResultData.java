package com.oim.core.api.data;

import com.only.common.result.Info;

/**
 * @author XiaHui
 * @date 2017-11-20 22:08:51
 */
public class ResultData<T> {

	ResultStatus status;
	T data;
	Info info = new Info();

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public void set(T data) {
		this.data = data;
	}

	public T get() {
		return data;
	}

	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return (status == ResultStatus.success) && info.isSuccess();
	}
}
