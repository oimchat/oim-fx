package com.im.base.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: XiaHui
 * @date: 2017年4月19日 下午4:42:15
 */
public class FileNameUtil {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSSS");

	public static String getSaveName(String name) {
		String date = format.format(new Date());
		StringBuilder saveName = new StringBuilder();// 拼接文件名称
		saveName.append(date);

		if (StringUtils.isNotBlank(name)) {
			saveName.append("_");
			if (name.length() > 150) {
				return name.substring(0, 150);
			} else {
				saveName.append(name);
			}
		}
		return saveName.toString();
	}

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

	/**
	 * 获取文件名，去掉后缀名
	 * 
	 * @param name
	 * @return
	 */
	public static String getName(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return "";
		} else {
			int index = fullName.lastIndexOf(".");
			if (index != -1) {
				return fullName.substring(0, index);
			}
		}
		return fullName;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param name
	 * @return
	 */
	public static String getSuffixName(String name) {
		if (StringUtils.isNotBlank(name)) {
			int length = name.length();
			int index = name.lastIndexOf(".");
			int cutIndex = (index + 1);
			if (index != -1) {
				if (cutIndex < length) {
					name = name.substring(cutIndex, length);
				}
			}
		}
		return name;
	}
}
