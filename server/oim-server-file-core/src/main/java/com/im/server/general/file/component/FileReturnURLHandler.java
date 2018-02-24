package com.im.server.general.file.component;

import com.im.base.common.box.ConfigBox;
import com.only.common.util.OnlyStringUtil;

/**
 * @author XiaHui
 * @date 2017-11-25 10:21:49
 */
public class FileReturnURLHandler {

	public static String getFileUrl(String nodePath) {
		String url = "";
		String path = ConfigBox.get("server.config.file", "server.config.file.request.path");
		if (OnlyStringUtil.isNotBlank(path)) {
			url = path + "/file/" + nodePath;
		}
		return url;
	}
}
