package com.im.server.general.file.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.im.server.general.file.bean.FileBaseData;

/**
 * @author XiaHui
 * @date 2017-11-24 22:32:28
 */
public class DownloadFileHandler {

	private final Logger logger = Logger.getLogger(this.getClass());

	public void downloadByIO(HttpServletRequest request, HttpServletResponse response, FileBaseData fd) {
		if(null!=fd){
			String fileName = fd.getName();
			String agent = request.getHeader("USER-AGENT");
			try {
				if (StringUtils.isNotBlank(agent)) {
					if ((agent.toLowerCase().indexOf("msie") > 0 || agent.toLowerCase().indexOf("rv:11") > 0)) {// IE下载乱码处理
						fileName = URLEncoder.encode(fileName, "UTF-8");
						fileName = fileName.replace("+", "%20");// 处理空格变“+”的问题
					} else if (agent.toLowerCase().indexOf("java") > 0) {// FF
					}
				} else {
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				}
			} catch (UnsupportedEncodingException e) {
				//e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			// response.setContentType("application/octet-stream");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + "");

			InputStream in = null;
			OutputStream out = null;
			try {
				StringBuilder fileSaveFullPathName = new StringBuilder();
				fileSaveFullPathName.append(fd.getRootPath());
				fileSaveFullPathName.append("/");
				fileSaveFullPathName.append(fd.getNodePath());
				fileSaveFullPathName.append(fd.getSaveName());

				File file = new File(fileSaveFullPathName.toString());
				if (file.exists()) {
					response.setHeader("Content-Length", file.length() + "");
					in = new FileInputStream(file);
					out = response.getOutputStream();
					byte[] b = new byte[2048];
					int length;
					while ((length = in.read(b)) > 0) {
						out.write(b, 0, length);
					}
				}
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				//e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {// 这里主要关闭。
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						//e.printStackTrace();
						logger.error(e.getMessage(), e);
					}
				}
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						//e.printStackTrace();
						logger.error(e.getMessage(), e);
					}
				}
			}
			// 返回值要注意，要不然就出现下面这句错误！
			// java+getOutputStream() has already been called for this
			// response
		}
	}
}
