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
import com.im.server.general.file.bean.FileData;
import com.im.server.general.file.component.AcceptFileHandler;
import com.im.server.general.file.component.data.FileInfo;
import com.im.server.general.file.dao.FileDataDAO;
import com.im.server.general.file.util.FilePathUtil;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年12月30日 下午8:59:57
 * @version 0.0.1
 */
@Service
@Transactional
public class FileService {

	private String upload = "upload";
	@Autowired
	FileDataDAO fileDataDAO;

	AcceptFileHandler acceptFileHandler = new AcceptFileHandler();

	public List<FileData> uploadByIO(HttpServletRequest request, String userId) {
		String basePath = request.getSession().getServletContext().getRealPath("/") + upload;
		String path =ConfigBox.get("server.config.file", "server.config.file.upload.path");
		StringBuilder rootPath = new StringBuilder((StringUtils.isBlank(path)) ? basePath : path);// 拼接文件上传目录
		String nodePath = FilePathUtil.getNodePath("file/");
		List<FileInfo> list = acceptFileHandler.uploadByIO(request, rootPath.toString(), nodePath);
		return add(list, userId);
	}

	private List<FileData> add(List<FileInfo> fileInfoList, String userId) {
		List<FileData> list = new ArrayList<FileData>();

		if (null != fileInfoList && !fileInfoList.isEmpty()) {
			for (FileInfo fi : fileInfoList) {
				File file = fi.getFile();
				long length = file.length();
				String rootPath = fi.getRootPath();
				String nodePath = fi.getNodePath();
				String saveName = fi.getSaveName();
				String name = fi.getName();
				String suffixName = FileNameUtil.getSuffixName(name);

				FileData fd = new FileData();
				fd.setName(name);
				fd.setSaveName(saveName);
				fd.setSuffixName(suffixName);
				fd.setUserId(userId);
				fd.setSize(length);
				fd.setRootPath(rootPath);
				fd.setNodePath(nodePath);
				fd.setCreateDate(new Date());
				fileDataDAO.saveOrUpdate(fd);

				list.add(fd);
			}
		}
		return list;
	}

	public FileData getFileData(String id) {
		return this.fileDataDAO.get(FileData.class, id);
	}
}
