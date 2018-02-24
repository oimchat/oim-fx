package com.im.server.general.file.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.im.base.common.util.FileNameUtil;
import com.im.base.common.util.KeyUtil;
import com.im.server.general.file.component.data.FileInfo;
import com.only.common.util.OnlyFileUtil;

/**
 * @author XiaHui
 * @date 2017-11-24 22:32:28
 */
public class AcceptFileHandler {

	private final Logger logger = Logger.getLogger(this.getClass());

	public List<FileInfo> uploadByIO(HttpServletRequest request, String rootPath, String nodePath) {
		List<FileInfo> infoList = new ArrayList<FileInfo>();
		List<MultipartFile> list = getList(request);
		if (!list.isEmpty()) {
			for (MultipartFile file : list) {
				FileInfo fileInfo = uploadByIO(file, rootPath, nodePath);
				if (null != fileInfo) {
					infoList.add(fileInfo);
				}
			}
		}
		return infoList;
	}

	public FileInfo uploadByIO(MultipartFile file, String rootPath, String nodePath) {
		StringBuilder path = new StringBuilder("");// 拼接文件上传目录
		path.append(rootPath);
		path.append("/");
		path.append(nodePath);

		String fileName = file.getOriginalFilename();// 取得当前上传文件的文件名称

		String key = KeyUtil.getKey();
		String suffixName = FileNameUtil.getSuffixName(fileName);
		String saveName = key + "." + suffixName;

		StringBuilder saveFullPathName = new StringBuilder();// 全路径和真实保存文件名
		saveFullPathName.append(path);
		saveFullPathName.append("/");
		saveFullPathName.append(saveName);
		OnlyFileUtil.checkOrCreateFolder(path.toString());
		File localFile = new File(saveFullPathName.toString());
		boolean mark = uploadByIO(file, localFile);
		FileInfo fileInfo = null;
		if (mark) {
			fileInfo = new FileInfo();
			fileInfo.setNodePath(nodePath);
			fileInfo.setRootPath(rootPath);
			fileInfo.setName(fileName);
			fileInfo.setSaveName(saveName);
			fileInfo.setFile(localFile);
		}
		return fileInfo;
	}

	public List<MultipartFile> getList(HttpServletRequest request) {
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		try {
			request.setCharacterEncoding("UTF-8");
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());// 创建一个通用的多部分解析器
			multipartResolver.setDefaultEncoding("UTF-8");
			if (multipartResolver.isMultipart(request)) {// 判断
															// request是否有文件上传,即多部分请求
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 转换成多部分request
				multiRequest.setCharacterEncoding("UTF-8");
				Iterator<String> fileIterator = multiRequest.getFileNames();// 取得request中的所有文件名
				while (fileIterator.hasNext()) {
					MultipartFile file = multiRequest.getFile(fileIterator.next());// 取得上传文件
					if (file != null) {
						list.add(file);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public boolean uploadByTransfer(MultipartFile file, File localFile) {
		boolean mark = true;
		try {
			file.transferTo(localFile);
		} catch (IOException e) {
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
			mark = false;
		}
		return mark;
	}

	public boolean uploadByIO(MultipartFile file, File localFile) {
		boolean mark = true;
		try {
			InputStream input = file.getInputStream();
			upload(input, localFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			mark = false;
		}
		return mark;
	}

	public void upload(InputStream input, File localFile) throws IOException {
		FileOutputStream out = null;
		try {
			if (null != input) {
				out = new FileOutputStream(localFile);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = input.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}
}
