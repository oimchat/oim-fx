package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.WordsFilter;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月15日 下午4:46:39
 */
@Repository
public class WordsFilterDAO extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<WordsFilter> getAllWordsFilterList() {
		List<WordsFilter> list = (List<WordsFilter>) this.readDAO.find("from " + WordsFilter.class.getName());
		return list;
	}
}
