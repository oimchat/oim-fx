package com.onlyxiahui.oim.client.resources.head;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlyxiahui.oim.client.resources.head.bean.HeadInfo;

/**
 * @author: XiaHui
 * @date: 2018-01-27 13:14:21
 */
public class HeadRepository {

	static final String defaultUserHeadPath = "/oim/common/images/head/user/";
	static final String defaultGroupHeadPath = "/oim/common/images/head/group/";

	static final String defaultUserHead = "";
	static final String defaultGroupHead = "";

	static final Map<String, URL> userHeadMap = new HashMap<String, URL>();
	static final Map<String, URL> groupHeadMap = new HashMap<String, URL>();
	static final Map<String, HeadInfo> userHeadListMap = new HashMap<String, HeadInfo>();
	static final Map<String, HeadInfo> groupHeadListMap = new HashMap<String, HeadInfo>();

	static {
		for (int i = 1; i < 101; i++) {

			String key = i + "";
			String path = defaultUserHeadPath + key;

			String file350 = path + "_350.png";
			String file100 = path + "_100.gif";
			String file40 = path + ".png";
			String file16 = path + "_16.bmp";

			URL url = HeadRepository.class.getResource(file350);

			HeadInfo hi = new HeadInfo();
			hi.setKey(key);
			hi.setUrl(url);

			userHeadListMap.put(key, hi);

			userHeadMap.put(key + "", url);
			userHeadMap.put(key + "_350", url);

			url = HeadRepository.class.getResource(file100);
			userHeadMap.put(key + "_100", url);

			url = HeadRepository.class.getResource(file40);
			userHeadMap.put(key + "_40", url);

			url = HeadRepository.class.getResource(file16);
			userHeadMap.put(key + "_16", url);
		}

		for (int i = 174; i < 265; i++) {
			String key = i + "";

			String path = defaultUserHeadPath + key;

			// String file350 = path + "_350.png";
			String file100 = path + "_100.gif";
			String file40 = path + ".png";
			String file16 = path + "_16.bmp";

			URL url = HeadRepository.class.getResource(file100);

			HeadInfo hi = new HeadInfo();
			hi.setKey(key);
			hi.setUrl(url);

			userHeadListMap.put(key + "", hi);

			userHeadMap.put(key + "", url);
			// userHeadMap.put(i + "_350", url);

			// url = HeadRepository.class.getResource(file100);
			userHeadMap.put(key + "_100", url);

			url = HeadRepository.class.getResource(file40);
			userHeadMap.put(key + "_40", url);

			url = HeadRepository.class.getResource(file16);
			userHeadMap.put(key + "_16", url);
		}

		for (int i = 1; i < 10; i++) {

			String key = i + "";

			String path = defaultGroupHeadPath + key;

			String file = path + ".png";
			URL url = HeadRepository.class.getResource(file);

			HeadInfo hi = new HeadInfo();
			hi.setKey(key);
			hi.setUrl(url);

			groupHeadListMap.put(key, hi);

			groupHeadMap.put(key + "", url);

		}
	}

	public URL getUserHeadPath(String key) {
		URL path = getUserHeadPath(key, 100);
		return path;
	}

	public URL getUserHeadPath(String key, int size) {
		String headKey = getUserHeadKey(key, size);
		URL path = userHeadMap.get(headKey);
		return path;
	}

	private String getUserHeadKey(String key, int size) {
		String headKey = key;
		if (userHeadMap.containsKey(key)) {
			if (size > 100) {
				headKey = key + "_350";
			} else if (100 >= size && size > 40) {
				headKey = key + "_100";
			} else if (40 >= size && size > 16) {
				headKey = key + "_40";
			} else if (16 >= size) {
				headKey = key + "_16";
			}
		}
		return headKey;
	}

	public URL getGroupHeadPath(String key) {
		URL path = groupHeadMap.get(key);
		return path;
	}

	public List<HeadInfo> getUserHeadPathList() {
		List<HeadInfo> list = new ArrayList<HeadInfo>(userHeadListMap.values());
		return list;
	}

	public List<HeadInfo> getGroupHeadPathList() {
		List<HeadInfo> list = new ArrayList<HeadInfo>(groupHeadListMap.values());
		return list;
	}
}
