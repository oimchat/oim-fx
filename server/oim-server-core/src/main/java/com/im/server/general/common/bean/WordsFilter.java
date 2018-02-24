package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

/**
 * @description:聊天过滤的敏感词
 * @author: Only
 * @date: 2016年8月15日 下午2:59:51
 */
@Entity(name = "im_words_filter")
public class WordsFilter extends BaseBean {

	private String words;
	private int level;

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
