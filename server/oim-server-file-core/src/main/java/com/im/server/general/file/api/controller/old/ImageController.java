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

import com.im.server.general.file.bean.ImageData;
import com.im.server.general.file.component.DownloadFileHandler;
import com.im.server.general.file.service.ImageService;
import com.only.common.result.ResultMessage;

/**
 * 图片上传下载接口
 * @author XiaHui
 * @date 2017-11-25 09:31:17
 *
 */
@Controller
@RequestMapping("/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	DownloadFileHandler dfh = new DownloadFileHandler();
	
	@ResponseBody
	@RequestMapping("/upload.do")
	public Object userUpload(HttpServletRequest request, HttpServletResponse response) {
		ResultMessage rm = new ResultMessage();
		String userId = request.getParameter("userId");
		List<ImageData> list = imageService.uploadByIO(request, userId);
		List<String> ids = new ArrayList<String>();
		for (ImageData fd : list) {
			ids.add(fd.getId());
		}
		rm.put("ids", ids);
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
