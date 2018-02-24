package com.oim.core.api.requester;

import java.util.List;

import com.oim.core.api.data.ResultData;
import com.onlyxiahui.im.bean.UserCategory;

/**
 * @author XiaHui
 * @date 2017-11-20 22:04:49
 */
public class UserRequester {

	public ResultData<List<UserCategory>> getUserCategoryList() {
		ResultData<List<UserCategory>> rd = new ResultData<List<UserCategory>>();
		return rd;
	}
}
