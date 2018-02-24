package com.onlyxiahui.im.module.upload;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: XiaHui
 * @date: 2017-04-20 10:07:09
 */
public class FileHttpUpload {

	public String upload(String http, String tagName, File file, Map<String, String> dataMap, FileAction<String> fileAction) {
		String result = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符

		OutputStream out = null;
		DataInputStream in = null;
		BufferedReader reader = null;
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

			out = new DataOutputStream(conn.getOutputStream());
			// text
			if (dataMap != null) {
				StringBuffer sb = new StringBuffer();
				Iterator<Map.Entry<String, String>> i = dataMap.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry<String, String> entry = i.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					sb.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					sb.append(inputValue);
				}
				out.write(sb.toString().getBytes());
			}

			// file
			if (file != null) {
				String inputName = tagName;
				String filename = file.getName();
				long size = file.length();

				StringBuffer sb = new StringBuffer();
				sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
				sb.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
				sb.append("Content-Type:application/octet-stream;charset=UTF-8" + "\r\n\r\n");

				out.write(sb.toString().getBytes());

				in = new DataInputStream(new FileInputStream(file));
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
			// 读取返回数据
			StringBuffer sb = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
			if (null != fileAction) {
				fileAction.done(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (null != fileAction) {
				fileAction.lost();
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void main(String[] arg) {
		FileAction<String> fileAction = new FileAction<String>() {
			@Override
			public void progress(long speed, long size, long up, double percentage) {

			}
			
			@Override
			public void lost() {
			}

			@Override
			public void done(String t) {
			}
		};
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId", "123456");
	
		String filepath = "E:/Temp/00.txt";
		String http = "http://127.0.0.1:8080/api/v1/oim/file/upload.do";
		FileHttpUpload fhu = new FileHttpUpload();
		String text = fhu.upload(http, "file", new File(filepath), dataMap, fileAction);
		System.out.println(text);
	}
}
