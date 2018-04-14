package com.only.common.web.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Only
 * @date 2016年5月19日 下午4:12:15
 */
public class RequestMessage extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> body = new HashMap<String, Object>();

	public RequestMessage() {
		putValue("body", body);
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
		putValue("body", body);
	}

	/**
	 * 添加body数据
	 * 
	 * @author: XiaHui
	 * @param key
	 * @param value
	 * @return
	 * @createDate: 2017年5月11日 上午10:13:41
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午10:13:41
	 */
	@Override
	public Object put(String key, Object value) {
		return body.put(key, value);
	}

	/**
	 * 
	 * 
	 * @author: XiaHui
	 * @param key
	 * @return
	 * @createDate: 2017年5月11日 上午10:13:56
	 * @update: XiaHui
	 * @updateDate: 2017年5月11日 上午10:13:56
	 */
	public Object get(String key) {
		return body.get(key);
	}

	public Object putValue(String key, Object value) {
		return super.put(key, value);
	}

	public Object getValue(String key) {
		return super.get(key);
	}
}
