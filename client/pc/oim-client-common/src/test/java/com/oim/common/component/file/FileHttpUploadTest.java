package com.oim.common.component.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.oim.common.component.file.action.FileAction;
import com.only.common.util.OnlyNumberUtil;

/**
 * @author: XiaHui
 * @date: 2018-02-02 16:34:33
 */
public class FileHttpUploadTest {

	FileHttpUpload fhu = new FileHttpUpload();

	@Test
	public void testUpload() {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", "110");

		FileAction<String> action = new FileAction<String>() {

			@Override
			public void progress(long speed, long size, long finishSize, double progress) {

				String speedText = "0MB/s";
				if (speed < 1024) {
					speedText = speed + "B/s";
				} else if (1024 <= speed && speed < (1024 * 1024)) {
					String s = OnlyNumberUtil.format(((double) speed / 1024d));
					speedText = s + "KB/s";
				} else {
					String s = OnlyNumberUtil.format(((double) speed / (double) (1024 * 1024)));
					speedText = s + "MB/s";
				}

				StringBuilder sb = new StringBuilder();
				sb.append("size:" + size);
				sb.append(" ");
				sb.append("finishSize:" + finishSize);
				sb.append(" ");
				sb.append("progress:" + progress);
				sb.append(" ");
				sb.append("speed:");
				sb.append(speedText);
				sb.append(" ");
				System.out.println(sb);
			}

			@Override
			public void success(String t) {
				System.out.println("success:" + t);

			}

			@Override
			public void lost(String t) {
				// TODO Auto-generated method stub

			}
		};
		String http = "http://127.0.0.1:12000/api/v1/oim/file/upload.do";
		File file = new File("E:/Release/oss.zip");
		fhu.upload(http, file, dataMap, action);
	}
}
