package com.im.server.general.file.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.server.general.file.bean.FileBaseData;
import com.im.server.general.file.bean.GroupHeadData;
import com.im.server.general.file.bean.UserHeadData;
import com.im.server.general.file.component.DownloadFileHandler;
import com.im.server.general.file.component.FileReturnURLHandler;
import com.im.server.general.file.service.HeadService;
import com.only.common.result.ResultMessage;

/**
 * 头像上传下载接口
 * @author XiaHui
 * @date 2017-11-25 09:30:45
 *
 */
@Controller
@RequestMapping("/api/v1/oim/head")
public class HeadUploadController {

	@Autowired
	HeadService headService;

	DownloadFileHandler dfh = new DownloadFileHandler();

	@CrossOrigin
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value ="/user/upload.do")
	public Object userUpload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String userId = request.getParameter("userId");
		List<UserHeadData> list = headService.userUploadByIO(request, userId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (null != list && !list.isEmpty()) {
			FileBaseData data = list.get(0);
			String name = data.getName();
			String saveName = data.getSaveName();
			String nodePath = data.getNodePath();

			dataMap.put("id", data.getId());
			dataMap.put("name", name);
			dataMap.put("size", data.getSize());
			dataMap.put("url", FileReturnURLHandler.getFileUrl(nodePath + saveName));
		}
		rm.put("data", dataMap);
		return rm;
	}

	@RequestMapping("/user/download.do")
	public void userDownload(String id, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(id)) {
			UserHeadData fd = headService.getUserHeadData(id);
			if (null != fd) {
				dfh.downloadByIO(request, response, fd);
			}
		}
	}
	
	
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value ="/group/upload.do")
	public Object upload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String groupId = request.getParameter("groupId");
		String userId = request.getParameter("userId");
		List<GroupHeadData> list = headService.groupUploadByIO(request, userId,  groupId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (null != list && !list.isEmpty()) {
			FileBaseData data = list.get(0);
			String name = data.getName();
			String saveName = data.getSaveName();
			String nodePath = data.getNodePath();

			dataMap.put("id", data.getId());
			dataMap.put("name", name);
			dataMap.put("size", data.getSize());
			dataMap.put("url", FileReturnURLHandler.getFileUrl(nodePath + saveName));
		}
		rm.put("data", dataMap);
		return rm;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST,value ="/group/download.do")
	public void download(String id, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(id)) {
			GroupHeadData fd = headService.getGroupHeadData(id);
			if (null != fd) {
				dfh.downloadByIO(request, response, fd);
			}
		}
	}
}
