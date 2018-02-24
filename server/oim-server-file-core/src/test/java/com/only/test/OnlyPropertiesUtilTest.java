package com.only.test;

import com.only.common.util.OnlyPropertiesUtil;

/**
 * @author XiaHui
 * @date 2017年5月31日 下午9:03:36
 */
public class OnlyPropertiesUtilTest {

	public static void main(String[] arg){
		String path = OnlyPropertiesUtil.getPropertyByClassPtah("/setting/config.properties", "file.save.path");
		System.out.println(path);
	}
}
