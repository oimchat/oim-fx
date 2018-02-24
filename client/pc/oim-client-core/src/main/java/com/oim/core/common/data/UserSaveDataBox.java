package com.oim.core.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.core.common.AppConstant;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月23日 上午12:21:59
 * @version 0.0.1
 */
public class UserSaveDataBox {

	public static final String path = AppConstant.getUserAppPath()+"Config/Application/UserSaveData.xml";
	private Map<String, UserSaveData> map = new HashMap<String, UserSaveData>();

	public Map<String, UserSaveData> getMap() {
		return map;
	}

	public void setMap(Map<String, UserSaveData> map) {
		this.map = map;
	}

	public void put(String key, UserSaveData value) {
		if(null==map){
			map = new HashMap<String, UserSaveData>();
		}
		map.put(key, value);
	}

	public UserSaveData get(String key) {
		return (null==map)?null:map.get(key);
	}
	
	public List<UserSaveData> getList() {
		return (null==map)?new ArrayList<UserSaveData>():new ArrayList<UserSaveData>(map.values());
	}

	public void remove(String key) {
		if(null!=map){
			map.remove(key);
		}
	}

	public int getSize() {
		return (null==map)?0:map.size();
	}

	public void remove(int count) {
		List<UserSaveData> list = new ArrayList<UserSaveData>(map.values());
		int size = list.size();
		int index = (count > size) ? size : count;
		for (int i = 0; i < index; i++) {
			map.remove(list.get(i).getAccount());
		}
	}
}
