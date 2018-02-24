package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.TextNotice;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.page.QueryPage;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月4日 下午9:48:59
 * @version 0.0.1
 */
@Repository
public class TextNoticeDAO extends BaseDAO {

	public void insertPush(String textNoticeId, List<String> outUserIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("textNoticeId", textNoticeId);
		if (null != outUserIds && !outUserIds.isEmpty()) {
			queryWrapper.put("outUserIds", outUserIds);
		}
		this.executeSQLByName("notice.insertPush", queryWrapper);
	}

	public List<TextNotice> getTextNoticeList(String userId, QueryPage page) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("userId", userId);
		List<TextNotice> list = this.queryPageListByName("notice.getTextNoticeList", queryWrapper, TextNotice.class);
		return list;
	}
}
