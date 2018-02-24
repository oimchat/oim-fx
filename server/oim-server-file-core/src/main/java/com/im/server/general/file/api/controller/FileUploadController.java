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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.im.server.general.file.bean.FileBaseData;
import com.im.server.general.file.bean.FileData;
import com.im.server.general.file.component.DownloadFileHandler;
import com.im.server.general.file.component.FileReturnURLHandler;
import com.im.server.general.file.service.FileService;
import com.only.common.result.ResultMessage;

/**
 * 
 * @author XiaHui
 * @date 2017-11-25 09:28:59
 *
 */
@Controller
@RequestMapping("/api/v1/oim/file")
public class FileUploadController {

	@Autowired
	FileService fileService;
	DownloadFileHandler dfh = new DownloadFileHandler();
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/uploads.do")
	public Object uploads(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		if (null != files) {
		}
		return rm;
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value ="/upload.do")
	public Object upload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String userId = request.getParameter("userId");
		List<FileData> list = fileService.uploadByIO(request, userId);

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

	@RequestMapping("/download.do")
	public void download(String id, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(id)) {
			FileData fileData = fileService.getFileData(id);
			if (null != fileData) {
				dfh.downloadByIO(request, response, fileData);
			}
		}
	}
}
