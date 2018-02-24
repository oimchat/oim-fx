package com.only.common.lib.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

/**
 * @author XiaHui
 * @date 2017-11-25 13:05:05
 */
public class OnlyHttpClient3Upload {

	public String upload(String http, String tagName, File file, Map<String, String> dataMap) {
		String body = null;
		PostMethod filePost = new PostMethod(http);

		try {
			if (dataMap != null) {
				// 通过以下方法可以模拟页面参数提交
				// filePost.setParameter("name", "中文");
				Iterator<Map.Entry<String, String>> i = dataMap.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry<String, String> entry = i.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					filePost.setParameter(inputName, inputValue);
				}
			}

			Part[] parts = { new FilePart(file.getName(), file) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			body = filePost.getResponseBodyAsString();
			if (status == HttpStatus.SC_OK) {// 上传成功
			} else {// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return body;
	}

	public static String uploads(String http, String tagName, List<File> files, Map<String, String> dataMap) {
		String body = null;
		PostMethod filePost = new PostMethod(http);

		try {
			filePost.setRequestHeader("Content-Type", "application/octet-stream;charset=UTF-8");
			if (dataMap != null) {
				// 通过以下方法可以模拟页面参数提交
				// filePost.setParameter("name", "中文");
				Iterator<Map.Entry<String, String>> i = dataMap.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry<String, String> entry = i.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					filePost.setParameter(inputName, inputValue);
				}
			}
			if (null != files && !files.isEmpty()) {
				int size = files.size();
				Part[] parts = new Part[size];
				for (int i = 0; i < size; i++) {
					File file = files.get(i);
					parts[i] = new FilePart(file.getName(), file);
				}
				filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			}

			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			body = filePost.getResponseBodyAsString();
			System.out.println(body);
			if (status == HttpStatus.SC_OK) {// 上传成功
			} else {// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return body;
	}
}
