package com.oim.test.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.oim.common.component.file.action.FileAction;
import com.oim.core.common.component.file.FileInfo;
import com.only.common.util.OnlyNumberUtil;

/**
 * @author: XiaHui
 * @date: 2017年4月20日 上午10:07:09
 */
public class FileHttpUpload {


	public String upload(String http, File file, Map<String, String> dataMap, FileAction<FileInfo> fileAction) {
		boolean success = true;
		FileInfo fi = new FileInfo();
		fi.setFile(file);
		String result = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(http);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (dataMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> i = dataMap.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry<String, String> entry = i.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (file != null) {

				String inputName = "FileData";
				String filename = file.getName();
				long size = file.length();

				StringBuffer sb = new StringBuffer();
				sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
				sb.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
				sb.append("Content-Type:application/octet-stream;charset=UTF-8" + "\r\n\r\n");

				out.write(sb.toString().getBytes());

				DataInputStream in = new DataInputStream(new FileInputStream(file));
				byte[] bufferOut = new byte[1024];

				int length = 0;
				long speed = 0;
				long up = 0;
				long time = System.currentTimeMillis();
				while ((length = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, length);
					speed = speed + length;
					up = up + length;
					long tempTime = System.currentTimeMillis();
					if (tempTime - time > 1000 && null != fileAction) {
						double percentage = (up > 0 && size > 0) ? ((double) up / (double) size) : 1d;
						fileAction.progress(speed, size, up, percentage);
						speed = 0;
						time = tempTime;
					}
				}
				in.close();
				if (null != fileAction) {
					double percentage = (up > 0 && size > 0) ? ((double) up / (double) size) : 1d;
					fileAction.progress(speed, size, up, percentage);
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
			reader.close();
			reader = null;
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
			if (null != fileAction) {
				if (success) {
					fileAction.success(fi);
				} else {
					fileAction.lost(fi);
				}
			}
		}
		return result;
	}

	public static void main(String[] arg) {
		FileAction<FileInfo> fileAction = new FileAction<FileInfo>() {

			@Override
			public void progress(long speed, long size, long up, double percentage) {

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

				System.out.println("size:" + size);
				System.out.println("up:" + up);
				System.out.println("percentage:" + percentage);
				System.out.println(speedText);
				System.out.println("");
			}

			@Override
			public void success(FileInfo fileData) {
				System.out.println("success id=" + fileData.getId());
			}

			@Override
			public void lost(FileInfo fi) {

			}

		};
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", "123456");
		// String filepath = "E:/Videos/Demo05-4k.mp4";
		String filepath = "E:/Temp/2.gif";
		String http = "http://47.92.117.28:8080/uploadservice/upload.api?uid=im&ticket=d39bf26b-ca54-4cb8-8f0f-6a74d8f25f88";
		FileHttpUpload fhu = new FileHttpUpload();
		String text = fhu.upload(http, new File(filepath), dataMap, fileAction);
		System.out.println(text);
	}
}
