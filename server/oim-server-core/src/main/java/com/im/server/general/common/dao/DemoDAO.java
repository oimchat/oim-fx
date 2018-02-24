package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.only.query.hibernate.QueryWrapper;
import com.onlyxiahui.im.message.data.UserData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年6月14日 下午4:17:01
 * @version 0.0.1
 */
@Repository
public class DemoDAO extends BaseDAO {

	
	public void demo() {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("account", "10001");
		List<UserData> list = this.queryListByName("demo.getUserByAccount", queryWrapper, UserData.class);
		System.out.println(list);
	}
	
}
