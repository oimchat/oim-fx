package com.oim.core.common.component;
/**
 * @author XiaHui
 * @date 2017年8月12日 下午3:48:39
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.oim.common.component.file.FileHttpDownload;
import com.oim.common.util.FileNameUtil;


public class DownloadImage {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 download("http://static.oschina.net/uploads/img/201602/18224123_YSM6.jpg","E:/Temp/Download/", "18224123_YSM6.jpg");
	}
	
	public static void download(String http,  String savePath, String fileName) throws Exception {
	    // 构造URL
	    URL url = new URL(http);
	    // 打开连接
	    boolean onlyReplaceName=false;
	    
	    
	    URLConnection urlConnection = url.openConnection();// 连接类的父类，抽象类
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;// http的连接类
//		if ("POST".equals(method) || "GET".equals(method)) {
//			httpURLConnection.setRequestMethod(method);// 设定请求的方法，默认是GET
//		}
		httpURLConnection.setRequestProperty("Charset", "UTF-8");// 设置字符编码
		httpURLConnection.connect();// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。

		String disposition = httpURLConnection.getHeaderField("Content-Disposition");
		String tempName = FileHttpDownload.getFileNameFromUrl(http);
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
		
	  
	    //设置请求超时为5s
		httpURLConnection.setConnectTimeout(5*1000);
	    // 输入流
	    InputStream is = httpURLConnection.getInputStream();
	
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	   File sf=new File(savePath);
	   if(!sf.exists()){
		   sf.mkdirs();
	   }
	   OutputStream os = new FileOutputStream(sf.getPath()+"\\"+saveName);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	} 

}

