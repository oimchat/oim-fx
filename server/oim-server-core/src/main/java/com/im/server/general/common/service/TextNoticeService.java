package com.im.server.general.common.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.server.general.common.bean.TextNotice;
import com.im.server.general.common.bean.UserTextNotice;
import com.im.server.general.common.dao.TextNoticeDAO;
import com.only.query.page.QueryPage;

/**
 * @author Only
 * @date 2016年5月20日 上午11:45:04
 */
@Service
@Transactional
public class TextNoticeService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	private TextNoticeDAO textNoticeDAO;

	public void save(TextNotice textNotice, List<String> userIds, String pushType) {
		if (null != textNotice) {
			textNotice.setCreateTime(new Date());
			textNotice.setTimestamp(System.currentTimeMillis());
			if (null == userIds || userIds.isEmpty()) {
				textNotice.setPushType(TextNotice.push_type_all);
				textNoticeDAO.save(textNotice);
				String textNoticeId = textNotice.getId();
				textNoticeDAO.insertPush(textNoticeId, userIds);
			} else {
				textNotice.setPushType(TextNotice.push_type_part);
				textNoticeDAO.save(textNotice);

				String textNoticeId = textNotice.getId();
				if ("1".equals(pushType)) {
					for (String userId : userIds) {
						UserTextNotice utn = new UserTextNotice();
						utn.setUserId(userId);
						utn.setTextNoticeId(textNoticeId);
						utn.setCreateTime(new Date());
						utn.setIsRead("0");
						textNoticeDAO.save(utn);
					}
				} else {
					textNoticeDAO.insertPush(textNoticeId, userIds);
				}
			}
		}
	}

	public List<TextNotice> getTextNoticeList(String userId, QueryPage page) {
		List<TextNotice> list = textNoticeDAO.getTextNoticeList(userId, page);
		return list;
	}
}
