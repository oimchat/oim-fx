package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.SecurityQuestion;
import com.only.query.hibernate.QueryWrapper;

/**
 * @author: XiaHui
 * @date: 2017年4月26日 下午3:53:06
 */
@Repository
public class SecurityQuestionDAO extends BaseDAO {

	/**
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年6月5日 下午3:26:03
	 * @update: XiaHui
	 * @updateDate: 2017年6月5日 下午3:26:03
	 */
	public List<SecurityQuestion> getListByUserId(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_security_question where userId=:userId");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		List<SecurityQuestion> list = this.readDAO.queryList(sb.toString(), queryWrapper, SecurityQuestion.class, null);
		return list;
	}
}
