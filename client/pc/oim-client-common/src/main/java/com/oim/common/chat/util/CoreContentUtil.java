package com.oim.common.chat.util;

import java.util.ArrayList;
import java.util.List;

import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

public class CoreContentUtil {

	/**
	 * 信息中是否包含图片
	 * 
	 * @author: XiaHui
	 * @param content
	 * @return
	 * @createDate: 2017年6月9日 上午11:30:20
	 * @update: XiaHui
	 * @updateDate: 2017年6月9日 上午11:30:20
	 */
	public static boolean hasImage(Content content) {
		boolean has = false;
		if (null != content) {
			List<Section> sections = content.getSections();
			if (null != sections) {
				for (Section s : sections) {
					List<Item> items = s.getItems();
					if (null != items) {
						for (Item i : items) {
							if (Item.type_image.equals(i.getType())) {
								has = true;
								break;
							}
						}
					}
				}
			}
		}
		return has;
	}

	/**
	 * 获取消息中的文字信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-10 10:52:21
	 * @param content
	 * @return
	 */
	public static String getText(Content content) {
		StringBuilder sb = new StringBuilder();
		if (null != content) {
			List<Section> sections = content.getSections();
			if (null != sections) {
				for (Section s : sections) {
					List<Item> items = s.getItems();
					if (null != items) {
						for (Item i : items) {
							if (Item.type_text.equals(i.getType())) {
								sb.append(i.getValue());
							}
							if (Item.type_face.equals(i.getType())) {
								sb.append("[表情]");
							}
							if (Item.type_image.equals(i.getType())) {
								sb.append("[图片]");
							}
							if (sb.length() > 60) {
								break;
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 提取信息中的图片信息列表
	 * 
	 * @author XiaHui
	 * @date 2018-01-19 09:44:46
	 * @param content
	 * @return
	 */
	public static List<Item> getImageItemList(Content content) {
		List<Item> imageList = new ArrayList<Item>();
		if (null != content) {
			List<Section> sections = content.getSections();
			if (null != sections) {
				for (Section s : sections) {
					List<Item> items = s.getItems();
					if (null != items) {
						for (Item i : items) {
							if (Item.type_image.equals(i.getType())) {
								imageList.add(i);
							}
						}
					}
				}
			}
		}
		return imageList;
	}
}
