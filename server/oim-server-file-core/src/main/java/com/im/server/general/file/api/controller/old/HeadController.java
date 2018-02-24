package com.im.server.general.file.api.controller.old;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.server.general.file.bean.GroupHeadData;
import com.im.server.general.file.bean.UserHeadData;
import com.im.server.general.file.component.DownloadFileHandler;
import com.im.server.general.file.service.HeadService;
import com.only.common.result.ResultMessage;

/**
 * 头像上传下载接口
 * @author XiaHui
 * @date 2017-11-25 09:30:45
 *
 */
@Controller
@RequestMapping("/head")
public class HeadController {

	@Autowired
	HeadService headService;

	DownloadFileHandler dfh = new DownloadFileHandler();

	@ResponseBody
	@RequestMapping("/user/upload.do")
	public Object userUpload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String userId = request.getParameter("userId");
		List<UserHeadData> list = headService.userUploadByIO(request, userId);
		List<String> ids = new ArrayList<String>();
		for (UserHeadData fd : list) {
			ids.add(fd.getId());
		}
		rm.put("ids", ids);
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
	
	
	
	@ResponseBody
	@RequestMapping("/group/upload.do")
	public Object upload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String groupId = request.getParameter("groupId");
		String userId = request.getParameter("userId");
		List<GroupHeadData> list = headService.groupUploadByIO(request, userId,  groupId);
		List<String> ids = new ArrayList<String>();
		for (GroupHeadData fd : list) {
			ids.add(fd.getId());
		}
		rm.put("ids", ids);
		return rm;
	}

	@RequestMapping("/group/download.do")
	public void download(String id, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(id)) {
			GroupHeadData fd = headService.getGroupHeadData(id);
			if (null != fd) {
				dfh.downloadByIO(request, response, fd);
			}
		}
	}
}
