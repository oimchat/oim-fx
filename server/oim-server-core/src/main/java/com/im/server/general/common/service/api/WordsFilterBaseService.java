package com.im.server.general.common.service.api;

import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.base.common.util.WordsParser;
import com.im.server.general.common.bean.WordsFilter;
import com.im.server.general.common.dao.WordsFilterDAO;

/**
 * 聊天内容中敏感词汇过滤。
 * 
 * @author: Only
 * @date: 2016年8月15日 下午3:02:50
 */
@Service
public class WordsFilterBaseService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	WordsParser wordsParser = new WordsParser();

	List<WordsFilter> wordsFilterList = null;
	@Resource
	WordsFilterDAO wordsFilterDAO;

	/**
	 * 敏感词汇过滤
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月19日 下午3:28:49
	 * @update: XiaHui
	 * @updateDate: 2016年8月19日 下午3:28:49
	 */
	public String wordsFilter(String text) {
		if (null == text || "".equals(text)) {
			return text;
		}
		if (null == wordsFilterList) {
			wordsFilterList = wordsFilterDAO.getAllWordsFilterList();
			wordsParser.clear();
			for (WordsFilter wf : wordsFilterList) {
				wordsParser.addFilterKeyWord(wf.getWords(), wf.getLevel());
			}
		}
		Vector<Integer> levelSet = new Vector<Integer>();
		return wordsParser.parse(text, levelSet);
	}
}
