package com.oim.common.chat.util;

import com.only.common.util.OnlyStringUtil;
import com.only.common.util.OnlySystemUtil;

/**
 * @author: XiaHui
 * @date: 2018-01-27 16:38:48
 */
public class WebImagePathUtil {
	/**
	 * 将图片在html的img标签中的本地图片src转成文件路径
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 10:44:24
	 * @param path
	 * @return
	 */
	public static String fileImageSourceToPath(String path) {
		String url = null;
		if (null != path) {
			if (path.startsWith("file:")) {
				int l = path.length();
				url = (l >= 5) ? path.substring(5, l) : path;
				// url = path.replace("file:", "");
				if (OnlySystemUtil.isWindows()) {
					char[] chars = url.toCharArray();
					int index = -1;
					for (int i = 0; i < chars.length; i++) {
						if ('/' != chars[i]) {
							break;
						}
						index++;
					}
					int length = url.length();
					if (index > -1 && index < length) {
						url = url.substring(index + 1, length);
					}
				}
				url = url.replace("\\", "/");
			} else {
				url = path;
			}
		}
		return url;
	}

	/**
	 * 本地图片文件路径转html<img> 标签src
	 * 
	 * @author: XiaHui
	 * @param path
	 * @return
	 * @createDate: 2017-12-25 15:48:00
	 * @update: XiaHui
	 * @updateDate: 2017-12-25 15:48:00
	 */
	public static String pathToFileImageSource(String path) {
		String temp = null;
		if (OnlyStringUtil.isNotBlank(path)) {
			if (path.startsWith("http")) {
				temp = path;
			} else if (path.startsWith("file:")) {
				temp = path;
			} else {
				String p = path.replace("\\", "/");
				if (p.startsWith("/")) {
					temp = "file:" + p;
				} else {
					temp = "file:/" + p;
				}
			}
		}
		return temp;
	}
}
