package com.test.db;

import com.oim.core.business.dao.UserInfoDAO;
import com.oim.core.common.bean.UserInfo;
import com.only.query.hibernate.QueryContext;
import com.only.query.hibernate.QueryItemException;

/**
 * @author XiaHui
 * @date 2017-11-13 21:58:25
 */
public class UserInfoDAOTest {

	public static void main(String[] args) {
		QueryContext bean = new QueryContext();
		bean.setConfigLocation("classpath:config/query/*.xml");
		try {
			bean.load();
		} catch (QueryItemException e) {
			e.printStackTrace();
		}
		UserInfo ui=new UserInfo();
		ui.setName("001");
//		UserInfoDAO uid=new UserInfoDAO();
//		uid.save(ui);
	}

}
