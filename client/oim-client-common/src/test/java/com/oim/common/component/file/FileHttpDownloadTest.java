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
public class FileHttpDownloadTest {

	

	@Test
	public void testUpload() {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", "110");

		FileAction<File> action = new FileAction<File>() {

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
			public void success(File t) {
				System.out.println(t.getAbsolutePath());
			}

			@Override
			public void lost(File t) {
				// TODO Auto-generated method stub

			}
		};
		String http = "http://download.netbeans.org/netbeans/8.2/final/bundles/netbeans-8.2-windows.exe";
		String savaPath = "Temp/";
		String fileName = "";

		boolean onlyReplaceName = false;
		FileHttpDownload fhd = new FileHttpDownload();
		fhd.download(http,"", savaPath, fileName, onlyReplaceName, action);
	}
}
