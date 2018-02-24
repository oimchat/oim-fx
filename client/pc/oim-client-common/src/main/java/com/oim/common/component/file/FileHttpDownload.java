package com.oim.common.component.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.oim.common.component.file.action.FileAction;
import com.oim.common.util.FileNameUtil;
import com.only.common.util.OnlyFileUtil;
import com.only.common.util.OnlyMD5Util;
import com.only.common.util.OnlyNumberUtil;

/**
 * @author: XiaHui
 * @date: 2017年4月20日 上午10:07:09
 */
public class FileHttpDownload extends FileHttpHandler {

	public void download(String http, String savaPath, String fileName, FileAction<File> fileAction) {
		download(http, savaPath, fileName, false, fileAction);
	}

	public void download(String http, String savaPath, String fileName, boolean onlyReplaceName, FileAction<File> fileAction) {
		download(http, "POST", savaPath, fileName, false, fileAction);
	}

	public void download(String http, String method, String savaPath, String fileName, boolean onlyReplaceName, FileAction<File> fileAction) {
		boolean success = true;
		File file = null;
		OutputStream out = null;
		BufferedInputStream in = null;
		URLConnection urlConnection = null;
		try {
			URL url = new URL(http);// 统一资源
			urlConnection = url.openConnection();// 连接类的父类，抽象类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;// http的连接类
			if ("POST".equals(method) || "GET".equals(method)) {
				httpURLConnection.setRequestMethod(method);// 设定请求的方法，默认是GET
			}
			httpURLConnection.setRequestProperty("Charset", "UTF-8");// 设置字符编码
			httpURLConnection.connect();// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。

			String disposition = httpURLConnection.getHeaderField("Content-Disposition");
			in = new BufferedInputStream(httpURLConnection.getInputStream());
			String saveName = getFileName(http, disposition, fileName, onlyReplaceName);

			String fileFullName = savaPath + saveName;
			int size = httpURLConnection.getContentLength();// 文件大小

			OnlyFileUtil.createParentFolder(fileFullName);

			file = new File(fileFullName);
			out = new FileOutputStream(file);

			this.handel(in, out, size, fileAction);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (null != fileAction) {
				if (success) {
					fileAction.success(file);
				} else {
					fileAction.lost(file);
				}
			}
		}
	}

	public void download(String http, String filePath, FileAction<File> fileAction) {
		download(http, new File(filePath), fileAction);
	}

	public void download(String http, File saveFile, FileAction<File> fileAction) {

		boolean success = true;
		File file = null;
		OutputStream out = null;
		BufferedInputStream in = null;
		URLConnection urlConnection = null;
		try {
			URL url = new URL(http);// 统一资源
			urlConnection = url.openConnection();// 连接类的父类，抽象类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;// http的连接类
			httpURLConnection.setRequestMethod("POST");// 设定请求的方法，默认是GET
			httpURLConnection.setRequestProperty("Charset", "UTF-8");// 设置字符编码
			httpURLConnection.connect();// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。

			String absolutePath = saveFile.getAbsolutePath();

			int size = httpURLConnection.getContentLength();// 文件大小
			in = new BufferedInputStream(httpURLConnection.getInputStream());

			OnlyFileUtil.createParentFolder(absolutePath);

			out = new FileOutputStream(saveFile);
			this.handel(in, out, size, fileAction);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (null != fileAction) {
			if (success) {
				fileAction.success(file);
			} else {
				fileAction.lost(file);
			}
		}
	}

	public static String getFileNameFromUrl(String url) {
		if (url == null) {
			return null;
		}

		try {
			// Add a protocol if none found
			if (!url.contains("//")) {
				url = "http://" + url;
			}
			URL uri = new URL(url);
			String result = FileNameUtil.getName(uri.getPath());

			if (result == null || result.isEmpty()) {
				return null;
			}
			if (result.contains("..")) {
				return null;
			}
			return result;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public String getFileName(String http, String disposition, String fileName, boolean onlyReplaceName) {

		String originalName = null;
		String saveName = null;

		if (null != disposition && !disposition.isEmpty()) {
			String[] array = disposition.split("filename=");
			if (array.length > 1) {
				originalName = array[1];
			}
		}

		if (null == originalName || originalName.isEmpty()) {
			originalName = getFileNameFromUrl(http);
		}

		if (null != fileName && !fileName.isEmpty()) {
			saveName = fileName;
		} else {
			saveName = originalName;
		}

		if (null == saveName || saveName.isEmpty()) {
			saveName = FileNameUtil.createSaveName("");
		}

		boolean hasOriginalName = (null != originalName && !originalName.isEmpty());
		boolean hasNewName = (null != fileName && !fileName.isEmpty());

		if (onlyReplaceName && hasNewName && hasOriginalName) {

			String name = FileNameUtil.getSimpleName(saveName);
			String suffixName = FileNameUtil.getSuffixName(originalName);
			if (null != suffixName && !suffixName.isEmpty()) {
				saveName = name + "." + suffixName;
			}
		}
		return saveName;
	}

	public String getFileNameO(String http, String disposition, String fileName, boolean onlyReplaceName) {

		String tempName = getFileNameFromUrl(http);
		String dispositionName = null;
		if (null != disposition && !"".equals(disposition)) {
			String[] array = disposition.split("filename=");
			if (array.length > 1) {
				dispositionName = array[1];
			}
		}
		String saveName = fileName;

		if (onlyReplaceName) {// 如果只是替换原文件的名称，不包含后缀名
			if (null != fileName && !"".equals(fileName)) {
				if (null != dispositionName && !"".equals(dispositionName)) {// 如果原文件名不为空
					String name = FileNameUtil.getSimpleName(fileName);
					String suffixName = FileNameUtil.getSuffixName(dispositionName);
					if (null == suffixName || "".equals(suffixName)) {
						saveName = fileName;
					} else {
						saveName = name + "." + suffixName;
					}
				} else if (null != tempName && !"".equals(tempName)) {
					String name = FileNameUtil.getSimpleName(fileName);
					String suffixName = FileNameUtil.getSuffixName(tempName);
					if (null == suffixName || "".equals(suffixName)) {
						saveName = fileName;
					} else {
						saveName = name + "." + suffixName;
					}
				} else {
					saveName = fileName;
				}

			} else {
				if (null != dispositionName && !"".equals(dispositionName)) {// 如果原文件名不为空
					saveName = dispositionName;
				} else if (null != tempName && !"".equals(tempName)) {
					saveName = tempName;
				} else {
					saveName = FileNameUtil.createSaveName("");
				}
			}

		} else {
			if (null != fileName && !"".equals(fileName)) {

			} else {
				if (null != dispositionName && !"".equals(dispositionName)) {// 如果原文件名不为空
					saveName = dispositionName;
				} else if (null != tempName && !"".equals(tempName)) {
					saveName = tempName;
				} else {
					saveName = FileNameUtil.createSaveName("");
				}
			}
		}
		return saveName;
	}

	public static void main(String[] arg) {
		FileAction<File> fileAction = new FileAction<File>() {

			@Override
			public void progress(long speed, long size, long finishSize, double percentage) {

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
				System.out.println("finishSize:" + finishSize);
				System.out.println("percentage:" + percentage);
				System.out.println(speedText);
				System.out.println("");
			}

			@Override
			public void success(File fileData) {
				// System.out.println("success id=" + fileData.getId());
				// System.out.println("success size=" + fileData.getSize());
			}

			@Override
			public void lost(File file) {

			}

		};
		String http = "http://static.oschina.net/uploads/img/201602/18224123_YSM6.jpg";
		File file = new File(http);
		System.out.println("file:" + file.getName());
		// System.out.println(file.exists());
		//
		// File newFile = new
		// File("E:/Temp/Download/6dd79b2f-3233-453e-b414-944819996818.jpg");
		// System.out.println("newFile:"+newFile.getAbsolutePath());

		String md5 = OnlyMD5Util.md5L32(http);
		// String http =
		// "http://127.0.0.1:8020/oim-file-server/file/download.do?id=6dd79b2f-3233-453e-b414-944819996818";
		FileHttpDownload fhd = new FileHttpDownload();
		fhd.download(http, "GET", "E:/Temp/Download/", md5, true, fileAction);
	}
}
