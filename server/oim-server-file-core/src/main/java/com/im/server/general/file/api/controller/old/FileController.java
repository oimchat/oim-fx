package com.im.server.general.file.api.controller.old;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.im.server.general.file.bean.FileData;
import com.im.server.general.file.component.DownloadFileHandler;
import com.im.server.general.file.service.FileService;
import com.only.common.result.ResultMessage;

/**
 * 
 * @author XiaHui
 * @date 2017-11-25 09:28:59
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	FileService fileService;
	DownloadFileHandler dfh = new DownloadFileHandler();

	@ResponseBody
	@RequestMapping("/uploads.do")
	public Object uploads(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		if (null != files) {
		}
		return rm;
	}

	@ResponseBody
	@RequestMapping("/upload.do")
	public Object upload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String userId = request.getParameter("userId");
		List<FileData> list = fileService.uploadByIO(request, userId);
		List<String> ids = new ArrayList<String>();
		for (FileData fd : list) {
			ids.add(fd.getId());
		}
		rm.put("ids", ids);
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
