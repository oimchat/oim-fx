package com.im.server.general.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.base.common.box.ConfigBox;
import com.im.base.common.util.FileNameUtil;
import com.im.base.dao.CommonDAO;
import com.im.server.general.file.bean.ImageData;
import com.im.server.general.file.component.AcceptFileHandler;
import com.im.server.general.file.component.data.FileInfo;
import com.im.server.general.file.util.FilePathUtil;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015-12-30 08:59:57
 * @version 0.0.1
 */
@Service
@Transactional
public class ImageService {

	private String upload = "upload";

	@Autowired
	CommonDAO commonDAO;
	
	AcceptFileHandler acceptFileHandler = new AcceptFileHandler();

	public List<ImageData> uploadByIO(HttpServletRequest request, String userId) {
		String basePath = request.getSession().getServletContext().getRealPath("/") + upload;
		String path =ConfigBox.get("server.config.file", "server.config.file.upload.path");
		StringBuilder rootPath = new StringBuilder((StringUtils.isBlank(path)) ? basePath : path);// 拼接文件上传目录
		String nodePath = FilePathUtil.getNodePath("images/");
		List<FileInfo> list = acceptFileHandler.uploadByIO(request, rootPath.toString(), nodePath);
		return add(list, userId);
	}

	private List<ImageData> add(List<FileInfo> fileInfoList, String userId) {
		List<ImageData> list = new ArrayList<ImageData>();

		if (null != fileInfoList && !fileInfoList.isEmpty()) {
			for (FileInfo fi : fileInfoList) {
				File file = fi.getFile();
				long length = file.length();
				String rootPath = fi.getRootPath();
				String nodePath = fi.getNodePath();
				String saveName = fi.getSaveName();
				String name = fi.getName();
				String suffixName = FileNameUtil.getSuffixName(name);

				ImageData fd = new ImageData();
				fd.setName(name);
				fd.setSaveName(saveName);
				fd.setSuffixName(suffixName);
				fd.setUserId(userId);
				fd.setSize(length);
				fd.setRootPath(rootPath);
				fd.setNodePath(nodePath);
				fd.setCreateDate(new Date());
				commonDAO.saveOrUpdate(fd);

				list.add(fd);
			}
		}
		return list;
	}

	public ImageData getImageData(String id) {
		return this.commonDAO.get(ImageData.class, id);
	}
}
