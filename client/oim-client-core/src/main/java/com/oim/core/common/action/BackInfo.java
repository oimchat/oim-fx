package com.oim.core.common.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaHui
 * @date 2017年6月17日 下午9:12:44
 */
public class BackInfo {

	private boolean success = true;
	private List<BackError> errors = new ArrayList<BackError>();

	public boolean isSuccess() {
		success = isEmpty(errors);
		return success;
	}

	public List<BackError> getErrors() {
		return errors;
	}

	public void setErrors(List<BackError> errors) {
		this.errors = errors;
	}

	public void addError(String code, String text) {
		if (errors == null) {
			errors = new ArrayList<BackError>();
		}
		errors.add(new BackError(code, text));
	}

	private boolean isEmpty(List<?> list) {
		return null == list || list.isEmpty();
	}
}
