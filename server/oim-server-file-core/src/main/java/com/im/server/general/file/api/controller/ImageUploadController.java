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
import com.im.server.general.file.bean.ImageData;
import com.im.server.general.file.component.DownloadFileHandler;
import com.im.server.general.file.component.FileReturnURLHandler;
import com.im.server.general.file.service.ImageService;
import com.only.common.result.ResultMessage;

/**
 * 图片上传下载接口
 * @author XiaHui
 * @date 2017-11-25 09:31:17
 *
 */
@Controller
@RequestMapping("/api/v1/oim/image")
public class ImageUploadController {

	@Autowired
	ImageService imageService;
	DownloadFileHandler dfh = new DownloadFileHandler();
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value ="/upload.do")
	public Object userUpload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String userId = request.getParameter("userId");
		List<ImageData> list = imageService.uploadByIO(request, userId);
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
	public void userDownload(String id, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(id)) {
			ImageData fileData = imageService.getImageData(id);
			if (null != fileData) {
				dfh.downloadByIO(request, response, fileData);
			}
		}
	}
}
