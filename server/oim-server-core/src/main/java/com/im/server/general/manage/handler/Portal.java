package com.im.server.general.manage.handler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: XiaHui
 * @date: 2016年9月28日 上午11:24:56
 */
@Controller
@RequestMapping("/")
public class Portal {

	@Resource
	WebHandler webHandler;

	@ResponseBody

	@RequestMapping(value = { "{path:\\w+}/web/api.do" })
	public Object api(@PathVariable("path") String path, HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		return webHandler.onMessage(data, request, response);
	}
}
