package com.oim.core.common.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FilenameUtils;

/**
 * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
 * 但不够简便；
 * 
 * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
 * 4.以输入流的形式获取返回内容 5.关闭输入流
 * 
 * @author H__D
 *
 */

/**
 * @author: XiaHui
 * @date: 2017年4月22日 上午11:05:53
 */
public class HttpConnectionUtil {

	/**
	 * 
	 * @param urlPath
	 *            下载路径
	 * @param downloadDir
	 *            下载存放目录
	 * @return 返回下载文件
	 */
	public static File downloadFile(String urlPath, String downloadDir) {
		File file = null;
		try {
			// 统一资源
			URL url = new URL(urlPath);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();

			// 文件大小
			int fileLength = httpURLConnection.getContentLength();

			// 文件名
			String filePathUrl = httpURLConnection.getURL().getFile();
			String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

			System.out.println("file length---->" + fileLength);
			System.out.println("filePathUrl---->" + fileLength);

			//URLConnection con = url.openConnection();

			BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

			String path = downloadDir + File.separatorChar + fileFullName;
			file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			int size = 0;
			//int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				//len += size;
				out.write(buf, 0, size);
				// 打印下载百分比
				// System.out.println("下载了-------> " + len * 100 / fileLength +
				// "%\n");
			}
			bin.close();
			out.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return file;
	}

	public static void main(String[] args) {
		// 下载文件测试
		// downloadFile("http://127.0.0.1:8080/oim-file-server/file/download.do?id=f4c61488-3dd7-4da1-a7db-b6ef9b03dcf4",
		// "E:/360Downloads");
		 file(
		 "http://127.0.0.1:8080/oim-file-server/file/download.do?id=cff4e115-f3e6-45eb-8892-307e65731a1d");
		//file();
	}

	public static void file() {
		String http = "http://127.0.0.1:8080/oim-file-server/file/download.do?id=cff4e115-f3e6-45eb-8892-307e65731a1d";
		try {
			URL url = new URL(http);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.addRequestProperty("host", url.getHost());
			con.addRequestProperty("referer", url.getHost());
			con.setDoInput(true);

//			BufferedInputStream in = new BufferedInputStream(con.getInputStream());

//			int bytes = 0;
//			byte[] bufferOut = new byte[1024];
//			while ((bytes = in.read(bufferOut)) != -1) {
//
//			}
//
//			in.close();
//			con.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void file(String urlPath) {

		try {
			//urlPath="https://repo.maven.apache.org/maven2/org/bytedeco/javacpp-presets/ffmpeg/3.0.2-1.2/ffmpeg-3.0.2-1.2-linux-x86_64.jar";
			//urlPath="https://codeload.github.com/fengyuanchen/cropper/zip/v3.0.0-rc";

			//FilenameUtils.getName(filename);
			
			System.out.println(getFilenameFromUrl(urlPath));
			URL url = new URL(urlPath);// 统一资源

			URLConnection urlConnection = url.openConnection();// 连接类的父类，抽象类
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();

			// 文件大小
			int fileLength = httpURLConnection.getContentLength();
			String getContentType=httpURLConnection.getContentType();
			String Disposition =httpURLConnection.getHeaderField("Content-Disposition");
			System.out.println("file Disposition---->" + Disposition);
			System.out.println("getContentType---->" + getContentType);
			// 文件名
			String filePathUrl = httpURLConnection.getURL().getFile();
			String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
			//BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
			//System.out.println("file type:"+HttpURLConnection.guessContentTypeFromStream(in));
			
			System.out.println("file length---->" + fileLength);
			System.out.println("filePathUrl---->" + filePathUrl);
			System.out.println("fileFullName---->" + fileFullName);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getFilenameFromUrl(String url)
	  {
	    if (url == null)
	      return null;

	    try
	    {
	      // Add a protocol if none found
	      if (! url.contains("//"))
	        url = "http://" + url;

	      URL uri = new URL(url);
	      String result = FilenameUtils.getName(uri.getPath());

	      if (result == null || result.isEmpty())
	        return null;

	      if (result.contains(".."))
	        return null;

	      return result;
	    }
	    catch (MalformedURLException e)
	    {
	      return null;
	    }
	  }
}
