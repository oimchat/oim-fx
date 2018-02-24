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
import com.im.server.general.file.bean.GroupHeadData;
import com.im.server.general.file.bean.UserHeadData;
import com.im.server.general.file.component.AcceptFileHandler;
import com.im.server.general.file.component.data.FileInfo;
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
public class HeadService {

	private String upload = "upload";
	@Autowired
	CommonDAO commonDAO;

	AcceptFileHandler acceptFileHandler = new AcceptFileHandler();

	public List<UserHeadData> userUploadByIO(HttpServletRequest request, String userId) {
		String basePath = request.getSession().getServletContext().getRealPath("/") + upload;
		String path =ConfigBox.get("server.config.file", "server.config.file.upload.path");
		StringBuilder rootPath = new StringBuilder((StringUtils.isBlank(path)) ? basePath : path);// 拼接文件上传目录
		String nodePath = FilePathUtil.getNodePath("head/user/");
		List<FileInfo> list = acceptFileHandler.uploadByIO(request, rootPath.toString(), nodePath);
		return addUserHead(list, userId);
	}

	private List<UserHeadData> addUserHead(List<FileInfo> fileInfoList, String userId) {
		List<UserHeadData> list = new ArrayList<UserHeadData>();

		if (null != fileInfoList && !fileInfoList.isEmpty()) {
			for (FileInfo fi : fileInfoList) {
				File file = fi.getFile();
				long length = file.length();
				String rootPath = fi.getRootPath();
				String nodePath = fi.getNodePath();
				String saveName = fi.getSaveName();
				String name = fi.getName();
				String suffixName = FileNameUtil.getSuffixName(name);

				UserHeadData fd = new UserHeadData();
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

	public List<GroupHeadData> groupUploadByIO(HttpServletRequest request, String userId, String groupId) {
		String basePath = request.getSession().getServletContext().getRealPath("/") + upload;
		String path =ConfigBox.get("config.file", "file.upload.path");
		StringBuilder rootPath = new StringBuilder((StringUtils.isBlank(path)) ? basePath : path);// 拼接文件上传目录
		String nodePath = FilePathUtil.getNodePath("head/group/");
		List<FileInfo> list = acceptFileHandler.uploadByIO(request, rootPath.toString(), nodePath);
		return addGroupHead(list, userId, groupId);
	}

	private List<GroupHeadData> addGroupHead(List<FileInfo> fileInfoList, String userId, String groupId) {
		List<GroupHeadData> list = new ArrayList<GroupHeadData>();

		if (null != fileInfoList && !fileInfoList.isEmpty()) {
			for (FileInfo fi : fileInfoList) {
				File file = fi.getFile();
				long length = file.length();
				String rootPath = fi.getRootPath();
				String nodePath = fi.getNodePath();
				String saveName = fi.getSaveName();
				String name = fi.getName();
				String suffixName = FileNameUtil.getSuffixName(name);

				GroupHeadData fd = new GroupHeadData();
				fd.setName(name);
				fd.setSaveName(saveName);
				fd.setSuffixName(suffixName);
				fd.setUserId(userId);
				fd.setGroupId(groupId);
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

	public UserHeadData getUserHeadData(String id) {
		return this.commonDAO.get(UserHeadData.class, id);
	}

	public GroupHeadData getGroupHeadData(String id) {
		return this.commonDAO.get(GroupHeadData.class, id);
	}
}
