/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.server.api;

import org.junit.Test;

import com.only.common.lib.util.HttpClient3Util;

public class ServerControllerTest {

	String url = "http://127.0.0.1:8080/api/v1/oim/server/getAddress.do";

	@Test
	public void getAddressTest() {
		String r = HttpClient3Util.post(url);
		System.out.println(r);
	}
}
