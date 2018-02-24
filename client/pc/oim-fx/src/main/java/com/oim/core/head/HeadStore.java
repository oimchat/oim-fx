package com.oim.core.head;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: XiaHui
 * @date: 2018-01-27 13:14:21
 */
public class HeadStore {

	static final String defaultUserHeadPath = "Resources/Images/Head/User/";
	static final String defaultGroupHeadPath = "Resources/Images/Head/Group/";

	static final String defaultUserHead = "";
	static final String defaultGroupHead = "";

	static final Map<String, String> userHeadMap = new HashMap<String, String>();
	static final Map<String, String> groupHeadMap = new HashMap<String, String>();
	static final Map<String, HeadData> userHeadListMap = new HashMap<String, HeadData>();
	static final Map<String, HeadData> groupHeadListMap = new HashMap<String, HeadData>();

	static {
		for (int i = 1; i < 101; i++) {

			String key = i + "";
			String path = defaultUserHeadPath + key;

			String file350 = path + "_350.png";
			String file100 = path + "_100.gif";
			String file40 = path + ".png";
			String file16 = path + "_16.bmp";

			File file=new File(file350);
			
			HeadData hi = new HeadData();
			hi.setKey(key);
			hi.setPath(file.getAbsolutePath());

			userHeadListMap.put(key, hi);

			userHeadMap.put(key + "", file.getAbsolutePath());
			userHeadMap.put(key + "_350", file.getAbsolutePath());

			file=new File(file100);
			userHeadMap.put(key + "_100", file.getAbsolutePath());

			file=new File(file40);
			userHeadMap.put(key + "_40", file.getAbsolutePath());

			file=new File(file16);
			userHeadMap.put(key + "_16", file.getAbsolutePath());
		}

		for (int i = 174; i < 265; i++) {
			String key = i + "";

			String path = defaultUserHeadPath + key;

			// String file350 = path + "_350.png";
			String file100 = path + "_100.gif";
			String file40 = path + ".png";
			String file16 = path + "_16.bmp";

			File file=new File(file100);
			HeadData hi = new HeadData();
			hi.setKey(key);
			hi.setPath(file.getAbsolutePath());

			userHeadListMap.put(key + "", hi);

			file=new File(file100);
			userHeadMap.put(key + "", file.getAbsolutePath());
			userHeadMap.put(key + "_100", file.getAbsolutePath());

			file=new File(file40);
			userHeadMap.put(key + "_40", file.getAbsolutePath());

			file=new File(file16);
			userHeadMap.put(key + "_16", file.getAbsolutePath());
		}

		for (int i = 1; i < 10; i++) {

			String key = i + "";

			String path = defaultGroupHeadPath + key;

			String filePath = path + ".png";

			File file=new File(filePath);
			HeadData hi = new HeadData();
			hi.setKey(key);
			hi.setPath(file.getAbsolutePath());

			groupHeadListMap.put(key, hi);

			groupHeadMap.put(key, file.getAbsolutePath());
		}
	}

	public String getUserHeadPath(String key) {
		String path = getUserHeadPath(key, 100);
		return path;
	}

	public String getUserHeadPath(String key, int size) {
		String headKey = getUserHeadKey(key, size);
		String path = userHeadMap.get(headKey);
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

	public String getGroupHeadPath(String key) {
		String path = groupHeadMap.get(key);
		return path;
	}

	public List<HeadData> getUserHeadPathList() {
		List<HeadData> list = new ArrayList<HeadData>(userHeadListMap.values());
		return list;
	}

	public List<HeadData> getGroupHeadPathList() {
		List<HeadData> list = new ArrayList<HeadData>(groupHeadListMap.values());
		return list;
	}
}
