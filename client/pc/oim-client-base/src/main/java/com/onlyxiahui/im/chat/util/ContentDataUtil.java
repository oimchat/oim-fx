package com.onlyxiahui.im.chat.util;

import java.util.ArrayList;
import java.util.List;

import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-26 17:02:09
 */
public class ContentDataUtil {

	public static Content createContent(String type, String value) {
		List<Section> sectionList = new ArrayList<Section>();

		if (Item.type_text.equals(type)) {
			String[] array = value.split("\n");
			for (String a : array) {
				Item item = new Item();
				item.setType(type);
				item.setValue(a);

				List<Item> itemList = new ArrayList<Item>();
				itemList.add(item);

				Section section = new Section();
				section.setItems(itemList);

				sectionList.add(section);
			}
		} else {

			Item item = new Item();
			item.setType(type);
			item.setValue(value);

			List<Item> itemList = new ArrayList<Item>();
			itemList.add(item);

			Section section = new Section();
			section.setItems(itemList);

			sectionList.add(section);
		}
		Content content = new Content();
		content.setSections(sectionList);
		return content;
	}
}
