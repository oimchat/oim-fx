package com.test.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

/**
 * @author XiaHui
 * @date 2017年5月29日 上午7:33:35
 */
public class GsonTest {

	public static void main(String[] args) {
		Gson gson = new Gson();
		
		List<Item> items=new ArrayList<Item>();
		Item item=new Item();
		
		Map<String,String> map=new HashMap<String,String>();
		
		map.put("name", "123.jpg");
		map.put("id", "1232323");
		
		//item.setValue(map);
		items.add(item);
		
		
		//ImageValue i=new ImageValue();
		//i.setValue("呵呵哈哈哈");
		//items.add(i);
		
		Section section=new Section();
		section.setItems(items);
		
		List<Section> sections=new ArrayList<Section>();
		sections.add(section);
		
		Content content=new Content();
		content.setSections(sections);
		
		String json=gson.toJson(content);
		System.out.println(json);
		
		Content c=gson.fromJson(json, Content.class);
		json=gson.toJson(c);
		System.out.println(json);
		List<Section> ss=c.getSections();
		if(null!=ss){
			for(Section s:ss){
				List<Item> is=s.getItems();
				if(null!=is){
//					for(Item m:is){
//						if(m.getValue() instanceof LinkedTreeMap){
//						
//							LinkedTreeMap<String, String> mp=(LinkedTreeMap<String, String>)m.getValue();
//							String name=mp.get("name");
//							System.out.println(name);
//						}
//					}
				}
			}
		}
	}

}
