package com.onlyxiahui.oim.face;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlyxiahui.oim.face.bean.FaceCategory;
import com.onlyxiahui.oim.face.bean.FaceInfo;

/**
 * 表情库
 * 
 * @author: XiaHui
 * @date: 2017-10-16 11:47:01
 */
public class FaceRepository {

	Map<String, FaceCategory> map = new HashMap<String, FaceCategory>();
	Map<String, Map<String, FaceInfo>> faceMap = new HashMap<String, Map<String, FaceInfo>>();
	List<FaceCategory> faceCategoryList = new ArrayList<FaceCategory>();

	public FaceRepository() {
		init();
	}

	/**
	 * 获取表情的url路径（classpath的路径）
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 09:19:31
	 * @param categoryId
	 * @param key
	 * @return
	 */
	public String getFacePath(String categoryId, String key) {
		String path = null;
		Map<String, FaceInfo> fm = faceMap.get(categoryId);
		if (null != fm) {
			FaceInfo fd = fm.get(key);
			if (null != fd) {
				path = fd.getRealPath();
			}
		}
		// if ("classical".equals(categoryId)) {
		// Map<String, FaceInfo> fm = faceMap.get(categoryId);
		// if (null != fm) {
		// FaceInfo fd = fm.get(key);
		// if (null != fd) {
		// path = fd.getRealPath();
		// }
		// }
		// } else if ("emotion".equals(categoryId)) {
		// String name = key + ".png";
		// URL url =
		// FaceRepository.class.getResource("/com/onlyxiahui/oim/face/emotion/"
		// + name);
		// if (null != url) {
		// path = url.toString();
		// }
		// }
		return path;
	}

	/**
	 * 获取表情的实体信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 09:20:10
	 * @param categoryId
	 * @param key
	 * @return
	 */
	public FaceInfo getFaceInfo(String categoryId, String key) {
		FaceInfo fd = null;
		Map<String, FaceInfo> fm = faceMap.get(categoryId);
		if (null != fm) {
			fd = fm.get(key);
		}
		return fd;
	}

	/**
	 * 获取所有表情分组及表情
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 09:20:29
	 * @return
	 */
	public List<FaceCategory> getAllFaceCategoryList() {
		List<FaceCategory> list = new ArrayList<FaceCategory>();
		list.addAll(faceCategoryList);
		return list;
	}

	/**
	 * 获取分组内的表情列表
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 09:20:45
	 * @param categoryId
	 * @return
	 */
	public List<FaceInfo> getFaceInfoList(String categoryId) {
		List<FaceInfo> list = new ArrayList<FaceInfo>();
		Map<String, FaceInfo> fm = faceMap.get(categoryId);
		if (null != fm) {
			list.addAll(fm.values());
		}
		return list;
	}

	/**
	 * put表情分组及表情
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 09:21:02
	 * @param faceCategory
	 */
	private void put(FaceCategory faceCategory) {
		if (null != faceCategory) {
			String categoryId = faceCategory.getId();
			FaceCategory temp = map.remove(categoryId);
			map.put(categoryId, faceCategory);
			if (null != temp) {
				faceCategoryList.remove(temp);
			}
			faceCategoryList.add(faceCategory);
			List<FaceInfo> list = faceCategory.getFaceInfoList();
			if (null != list && !list.isEmpty()) {
				for (FaceInfo fd : list) {
					put(categoryId, fd.getKey(), fd);
				}
			}
		}
	}

	private void put(String categoryId, String key, FaceInfo faceInfo) {
		Map<String, FaceInfo> fm = faceMap.get(categoryId);
		if (null == fm) {
			fm = new HashMap<String, FaceInfo>();
			faceMap.put(categoryId, fm);
		}
		fm.put(key, faceInfo);
	}

	private void init() {
		initClassical();
		initEmoji();
		initEmotion();
	}

	private void initClassical() {

		// String realPath = "/com/onlyxiahui/oim/face/classical/";
		// String showPath = "/com/onlyxiahui/oim/face/classical/";
		// URL url = FaceManager.class.getResource("/com/onlyxiahui/oim/face/");
		// try {
		// File file = new File(url.toURI());
		// if (file.exists()) {
		//
		// }
		// } catch (URISyntaxException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }

		FaceCategory fc = new FaceCategory();
		fc.setId("classical");
		fc.setName("经典");

		List<FaceInfo> fdList = new ArrayList<FaceInfo>();
		List<String> nameList = new ArrayList<String>();

		InputStream is = FaceRepository.class.getResourceAsStream("/com/onlyxiahui/oim/face/classical/face.txt");
		InputStreamReader isr = null;
		BufferedReader reader = null;
		String line = null;
		try {
			isr = new InputStreamReader(is, "UTF-8");
			reader = new BufferedReader(isr);
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					nameList.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != isr) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String path = "/com/onlyxiahui/oim/face/classical/";
		String showSuffix = ".png";
		String realSuffix = ".gif";

		for (String name : nameList) {
			String[] array = name.split(",");
			if (array.length > 1) {
				String key = array[0];
				String text = array[1];
				String showFileName = key + showSuffix;
				String realFileName = key + realSuffix;

				if (key.length() > 3) {
					URL showUrl = FaceRepository.class.getResource(path + "extra/" + showFileName);
					URL realUrl = FaceRepository.class.getResource(path + "extra/" + realFileName);

					FaceInfo fd = new FaceInfo();
					fd.setCategoryId(fc.getId());
					fd.setKey(key);
					fd.setShowPath(showUrl.toString());
					fd.setRealPath(realUrl.toString());
					fd.setText(text);
					fd.setHeight(40);
					fd.setWidth(40);
					fdList.add(fd);
				} else {
					URL showUrl = FaceRepository.class.getResource(path + "png/" + showFileName);
					URL realUrl = FaceRepository.class.getResource(path + "gif/" + realFileName);

					FaceInfo fd = new FaceInfo();
					fd.setCategoryId(fc.getId());
					fd.setKey(key);
					fd.setShowPath(showUrl.toString());
					fd.setRealPath(realUrl.toString());
					fd.setText(text);
					fd.setHeight(40);
					fd.setWidth(40);
					fdList.add(fd);
				}
			}
		}

		if (!fdList.isEmpty()) {
			fc.setFaceInfoList(fdList);
			put(fc);
		}
	}

	private void initEmoji() {

	}

	private void initEmotion() {
		FaceCategory fc = new FaceCategory();
		fc.setId("emotion");
		fc.setName("Big");

		List<FaceInfo> fdList = new ArrayList<FaceInfo>();
		InputStream is = FaceRepository.class.getResourceAsStream("/com/onlyxiahui/oim/face/emotion/face.txt");
		List<String> nameList = new ArrayList<String>();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					nameList.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = "/com/onlyxiahui/oim/face/emotion/";
		String suffix = ".png";

		for (String name : nameList) {
			String[] array = name.split(",");
			if (array.length > 1) {
				String key = array[0];
				String text = array[1];
				String fileName = key + suffix;

				URL url = FaceRepository.class.getResource(path + fileName);

				FaceInfo fd = new FaceInfo();
				fd.setCategoryId(fc.getId());
				fd.setKey(key);
				fd.setShowPath(url.toString());
				fd.setRealPath(url.toString());
				fd.setText(text);
				// fd.setHeight(40);
				// fd.setWidth(40);
				fdList.add(fd);
			}
		}

		if (!fdList.isEmpty()) {
			fc.setFaceInfoList(fdList);
			put(fc);
		}
	}
}
