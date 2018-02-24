package com.im.server.general.file.util;

import org.apache.commons.lang3.StringUtils;

import com.only.common.util.OnlyDateUtil;

/**
 * @author XiaHui
 * @date 2017-11-24 22:52:39
 */
public class FilePathUtil {

	/**
	 * 获取根目录
	 * 
	 * @param rootPath
	 * @return
	 */
	public static StringBuilder getRootPath(String rootPath) {
		StringBuilder root = new StringBuilder();
		return root.append((StringUtils.isNotBlank(rootPath)) ? rootPath : "");
	}

	public static String getNodePath() {
		return getNodePath("");
	}

	public static String getNodePath(String basePath) {
		StringBuilder nodePath = new StringBuilder(basePath);
		nodePath.append(OnlyDateUtil.getCurrentYear());
		nodePath.append("/");
		nodePath.append(OnlyDateUtil.getCurrentMonth());
		nodePath.append("/");
		nodePath.append(OnlyDateUtil.getCurrentDay());
		nodePath.append("/");
		return nodePath.toString();
	}
}
