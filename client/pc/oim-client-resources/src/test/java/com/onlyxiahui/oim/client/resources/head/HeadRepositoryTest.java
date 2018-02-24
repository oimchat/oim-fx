package com.onlyxiahui.oim.client.resources.head;

import java.util.List;

import com.onlyxiahui.oim.client.resources.head.bean.HeadInfo;

/**
 * @author: XiaHui
 * @date: 2018-01-27 16:04:44
 */
public class HeadRepositoryTest {

	public static void main(String[] args) {
		HeadRepository hr=new HeadRepository();
		List<HeadInfo> list=hr.getUserHeadPathList();
		for(HeadInfo path:list){
			System.out.println(path);
		}
	}
}
