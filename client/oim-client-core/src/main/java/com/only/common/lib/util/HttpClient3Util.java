package com.only.common.lib.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;

/**
 * @author XiaHui
 * @date 2015年1月21日 上午11:17:47
 */
public class HttpClient3Util {

	public static String post(String url) {
		Map<String, String> dataMap = new HashMap<String, String>();
		return post(url, dataMap, 3000, "UTF-8", "UTF-8", false);
	}

	public static String post(String url, Map<String, String> dataMap) {
		return post(url, dataMap, 3000, "UTF-8", "UTF-8", false);
	}

	public static String post(String url, Map<String, String> dataMap, String charset) {
		return post(url, dataMap, 3000, "UTF-8", charset, false);
	}

	public static String post(String url, Map<String, String> dataMap, int timeOut, String sendCharset, String returnCharset, boolean urlCharset) {

		String text = "";
		if (urlCharset) {
			int lastIndex = url.lastIndexOf("?");
			if (lastIndex != -1) {
				String temp = url.substring(lastIndex + 1, url.length());
				dataMap.putAll(getDataMap(temp));
				url = url.substring(0, lastIndex);
			}
		}

		PostMethod postMethod = new PostMethod(url);
		InputStream in = null;
		BufferedReader reader = null;
		try {
			if (null != sendCharset) {
				postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + sendCharset);
			}

			if (null != dataMap && !dataMap.isEmpty()) {
				Set<String> keySet = dataMap.keySet();
				for (String key : keySet) {
					postMethod.addParameter(key, dataMap.get(key));
				}
			}
			HttpClient client = new HttpClient();

			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(postMethod);
			if (null != returnCharset) {
				StringBuilder sb = new StringBuilder();
				in = postMethod.getResponseBodyAsStream();
				reader = new BufferedReader(new InputStreamReader((in), returnCharset));
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				text = sb.toString();
			} else {
				text = postMethod.getResponseBodyAsString();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// postMethod.releaseConnection();
		}
		return text;
	}

	public static String get(String url) {
		String body = null;
		GetMethod getMethod = new GetMethod(url);
		InputStream in = null;
		BufferedReader reader = null;
		try {

			getMethod.addRequestHeader("Content-type", "text/html;charset=UTF-8");
			getMethod.getParams().setContentCharset("UTF-8");

			HttpClient client = new HttpClient();

			client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
			client.executeMethod(getMethod);
			in = getMethod.getResponseBodyAsStream();

			body = getMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			getMethod.releaseConnection();
		}
		return body;
	}

	public String get(String url, Map<String, String> dataList, Charset sendCharset, Charset returnCharset, int timeOut, boolean urlCharset) {
		StringBuilder text = new StringBuilder();
		if (urlCharset) {
			int lastIndex = url.lastIndexOf("?");
			if (lastIndex != -1) {
				String temp = url.substring(lastIndex + 1, url.length());
				dataList.putAll(getDataMap(temp));
				url = url.substring(0, lastIndex);
			}
		}
		try {
			url = getUrl(url, dataList, sendCharset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		GetMethod getMethod = new GetMethod(url);
		InputStream in = null;
		BufferedReader reader = null;
		try {

			getMethod.addRequestHeader("Content-type", "text/html;charset=" + sendCharset.name());
			getMethod.getParams().setContentCharset(sendCharset.name());

			HttpClient client = new HttpClient();

			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(getMethod);
			in = getMethod.getResponseBodyAsStream();

			reader = new BufferedReader(new InputStreamReader((in), returnCharset));
			String line = "";
			while ((line = reader.readLine()) != null) {
				text.append(line);
			}
			// body = postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			getMethod.releaseConnection();
		}
		return text.toString();
	}

	public static String getUrl(String url, Map<String, String> dataMap, Charset charset) throws UnsupportedEncodingException {
		StringBuilder value = new StringBuilder(url);
		if (StringUtils.isNotBlank(url)) {
			boolean isFirst = false;
			if (null != dataMap && !dataMap.isEmpty()) {
				int index = url.indexOf("?");
				int lastIndex = url.lastIndexOf("?");
				if (index == -1) {
					value.append("?");
					isFirst = true;
				}
				if (lastIndex == (url.length() - 1)) {
					isFirst = true;
				}
			}

			Set<String> keySet = dataMap.keySet();
			for (String key : keySet) {
				String data = dataMap.get(key);
				if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(data)) {
					if (!isFirst) {
						value.append("&");
					}
					value.append(key);
					value.append("=");
					value.append((null == charset) ? data : URLEncoder.encode(data, charset.name()));
					isFirst = false;
				}
			}
		}
		return value.toString();
	}

	public static Map<String, String> getDataMap(String urlValue) {
		Map<String, String> dataMap = new HashMap<String, String>();
		if (StringUtils.isNotBlank(urlValue)) {
			String[] values = urlValue.split("&");
			if (values.length > 0) {
				for (String d : values) {
					String[] dataArray = d.split("=");
					if (dataArray.length == 2) {
						String name = (dataArray[0]);
						String value = (dataArray[1]);
						dataMap.put(name, value);
					}
				}
			}
		}
		return dataMap;
	}

	public static void main(String[] arg) {

	}
}
