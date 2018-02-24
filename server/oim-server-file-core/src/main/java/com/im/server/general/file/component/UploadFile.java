package com.im.server.general.file.component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.im.base.common.util.FileNameUtil;
import com.im.base.common.util.KeyUtil;
import com.im.server.general.file.bean.FileData;
import com.im.server.general.file.util.FilePathUtil;
import com.only.common.util.OnlyFileUtil;

/**
 * @author XiaHui
 * @date 2014-12-25 11:14:14
 */
public class UploadFile {

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param uploadData
	 * @param basePath
	 * @return
	 */
	public List<FileData> uploadByIo(HttpServletRequest request, String rootPath, String nodePath, String userId) {
		StringBuilder tempRootPath = FilePathUtil.getRootPath(rootPath);
		List<FileData> fileDataList = new ArrayList<FileData>();
		try {
			request.setCharacterEncoding("UTF-8");
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());// 创建一个通用的多部分解析器
			multipartResolver.setDefaultEncoding("UTF-8");
			if (multipartResolver.isMultipart(request)) {// 判断
															// request是否有文件上传,即多部分请求
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 转换成多部分request
				multiRequest.setCharacterEncoding("UTF-8");
				Iterator<String> fileIterator = multiRequest.getFileNames();// 取得request中的所有文件名
				FileData fileData = null;
				while (fileIterator.hasNext()) {
					MultipartFile file = multiRequest.getFile(fileIterator.next());// 取得上传文件
					if (file != null) {
						String fileName = file.getOriginalFilename();// 取得当前上传文件的文件名称
						if (null != fileName && !"".equals(fileName.trim())) {// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
							// fileName=new
							// String(fileName.getBytes("ISO-8859-1"),"UTF-8");
							String key = KeyUtil.getKey();
							String suffixName = FileNameUtil.getSuffixName(fileName);
							String saveName = key + "." + suffixName;

							StringBuilder saveFullName = new StringBuilder();// 真实保存到服务器的文件名称
							saveFullName.append(saveName);
							saveFullName.append(".");
							saveFullName.append(suffixName);

							StringBuilder saveFullPathName = new StringBuilder(tempRootPath);// 全路径和真实保存文件名
							saveFullPathName.append(nodePath);
							saveFullPathName.append(saveFullName);

							OnlyFileUtil.checkOrCreateFolder(saveFullPathName.toString());
							File localFile = new File(saveFullPathName.toString());
							file.transferTo(localFile);
							long size = file.getSize();

							fileData = new FileData();

							fileData.setName(fileName);
							fileData.setSaveName(saveFullName.toString());

							fileData.setRootPath(tempRootPath.toString());
							fileData.setNodePath(nodePath);
							fileData.setSuffixName(suffixName);
							fileData.setSize(size);
							fileData.setCreateDate(new Date());
							fileData.setUserId(userId);
							fileDataList.add(fileData);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return fileDataList;
	}
}
