/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.im.server.general.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.base.common.box.ConfigBox;
import com.only.common.result.ResultMessage;
import com.only.general.annotation.parameter.RequestParameter;

/**
 *
 * @author Only
 */
@Controller
@RequestMapping("/api/v1/oim/server")
public class ServerController {

	/**
	 * data={body:{}}
	 * 
	 * @author XiaHui
	 * @date 2017-11-26 09:12:37
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@RequestParameter
	@RequestMapping(method = RequestMethod.POST, value = "/getAddress.do")
	public Object address(HttpServletRequest request) {
		ResultMessage rm = new ResultMessage();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String category = "";
		String key = "";
		String value = "";
		Map<String, Object> map = null;
		
		// 1、main
		////////////////////////
		map = new HashMap<String, Object>();
		map.put("key", "server.main.tcp.address");
		map.put("type", "2");

		category = "server.path.im";
		// 聊天服务地址
		key = "im.server.tcp.address";
		value = ConfigBox.get(category, key);
		map.put("address", value);
		// 聊天服务端口
		key = "im.server.tcp.port";
		value = ConfigBox.get(category, key);
		map.put("port", Integer.parseInt(value));
		list.add(map);
		//////////////////////////

		////////////////////////
		map = new HashMap<String, Object>();
		map.put("key", "server.main.websocket.address");
		map.put("type", "4");
		// websocket地址
		key = "im.server.websocket.path";
		value = ConfigBox.get(category, key);
		map.put("address", value);
		list.add(map);
		//////////////////////////

		////////////////////////
		map = new HashMap<String, Object>();
		map.put("key", "server.main.http.address");
		map.put("type", "1");
		// 聊天服务http地址
		key = "im.server.http.url";
		value = ConfigBox.get(category, key);
		map.put("address", value);
		list.add(map);
		//////////////////////////
		
		////////////////////////
		// 2、远程
		/**
		 * 远程服务
		 */
		map = new HashMap<String, Object>();
		map.put("key", "server.remote.tcp.address");
		map.put("type", "2");

		category = "server.path.remote";
		// 远程服务地址
		key = "remote.server.tcp.address";
		value = ConfigBox.get(category, key);
		map.put("address", value);
		// 远程服务端口
		key = "remote.server.tcp.port";
		value = ConfigBox.get(category, key);
		map.put("port", Integer.parseInt(value));
		list.add(map);
		//////////////////////////

		////////////////////////
		/**
		 * 文件服务地址
		 */
		map = new HashMap<String, Object>();
		map.put("key", "server.file.http.address");
		map.put("type", "1");

		category = "server.path.file";
		key = "file.server.http.url";
		value = ConfigBox.get(category, key);
		map.put("address", value);
		list.add(map);
		//////////////////////////

		rm.put("list", list);
		return rm;
	}
}
