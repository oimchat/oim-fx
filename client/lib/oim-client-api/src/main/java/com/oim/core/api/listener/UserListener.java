package com.oim.core.api.listener;

import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

/**
 * @author XiaHui
 * @date 2017-11-20 21:55:09
 */
public interface UserListener {

	void dataChange(UserData userData);

	void statusChange(String userId, String status);

	void headChange(UserHead head);
}
