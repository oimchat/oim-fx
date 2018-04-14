package com.oim.fx.common.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

public class ContentUtil {


	public static Content getContent(String html, String fontName, int fontSize, String color, boolean underline, boolean bold, boolean italic) {
		Document htmlDocument = Jsoup.parse(html);
		List<Section> sectionList = new ArrayList<Section>();
		Content content = null;
		if (null != htmlDocument) {
			List<org.jsoup.nodes.Element> elementList = htmlDocument.getElementsByTag("body");

			if (!elementList.isEmpty()) {
				org.jsoup.nodes.Element body = elementList.get(0);
				List<org.jsoup.nodes.Node> nodeList = body.childNodes();

				if (null != nodeList && !nodeList.isEmpty()) {

					List<Item> itemList = new ArrayList<Item>();

					for (org.jsoup.nodes.Node node : nodeList) {
						if (node instanceof org.jsoup.nodes.TextNode) {
							String value = ((org.jsoup.nodes.TextNode) node).text();
							if (null != value && !"".equals(value)) {
								Item item = new Item();
								item.setType(Item.type_text);
								item.setValue(value);
								itemList.add(item);
							}
						} else if (node instanceof org.jsoup.nodes.Element) {
							org.jsoup.nodes.Element n = (org.jsoup.nodes.Element) node;
							String tagName = n.tagName();

							if ("img".equals(tagName)) {
								String name = n.attr("name");
								String value = n.attr("value");
								String src = n.attr("src");

								if ("face".equals(name)) {
									Item item = new Item();
									item.setType(Item.type_face);
									item.setValue(value);
									itemList.add(item);
								} else {// 如果是发送图片，因为传送文件有点麻烦，就直接将图片转成String，在接受段解析回来就可以了
									if (null != src && !"".equals(src)) {
										// String image = handleImagePath(src);
										Item item = new Item();
										item.setType(Item.type_image);
										item.setValue(src);
										itemList.add(item);
									}
								}
							} else if ("br".equals(tagName)) {
								
								Section section = new Section();
								section.setItems(itemList);
								sectionList.add(section);
								
								itemList = new ArrayList<Item>();
								
							} else if ("div".equals(tagName)) {
								if (!itemList.isEmpty()) {
									Section section = new Section();
									section.setItems(itemList);
									sectionList.add(section);
									itemList = new ArrayList<Item>();
								}
								Section s = getSection(n);
								sectionList.add(s);
							} else {
								String value = n.text();
								if (null != value && !"".equals(value)) {
									Item item = new Item();
									item.setType(Item.type_text);
									item.setValue(value);
									itemList.add(item);
								}
							}
						}
					}
					if (!itemList.isEmpty()) {
						Section section = new Section();
						section.setItems(itemList);
						sectionList.add(section);
					}
				} 
			}

			if (!sectionList.isEmpty()) {
				content = new Content();
				com.onlyxiahui.im.message.data.chat.Font font = new com.onlyxiahui.im.message.data.chat.Font();
				font.setBold(bold);
				font.setColor(color);
				font.setItalic(italic);
				font.setName(fontName);
				font.setSize(fontSize);
				font.setUnderline(underline);

				content.setFont(font);
				content.setSections(sectionList);
			}
		}
		return content;
	}

	private static Section getSection(org.jsoup.nodes.Element e) {
		Section section = new Section();
		List<org.jsoup.nodes.Node> nodeList = e.childNodes();
		if (null != nodeList && !nodeList.isEmpty()) {
			List<Item> itemList = new ArrayList<Item>();
			for (org.jsoup.nodes.Node node : nodeList) {
				if (node instanceof org.jsoup.nodes.Element) {
					org.jsoup.nodes.Element n = (org.jsoup.nodes.Element) node;
					if ("img".equals(n.tagName())) {
						String name = n.attr("name");
						String value = n.attr("value");
						String src = n.attr("src");

						if ("face".equals(name)) {
							Item item = new Item();
							item.setType(Item.type_face);
							item.setValue(value);
							itemList.add(item);
						} else {// 如果是发送图片，因为传送文件有点麻烦，就直接将图片转成String，在接受段解析回来就可以了
							if (null != src && !"".equals(src)) {
								Item item = new Item();
								item.setType(Item.type_image);
								item.setValue(src);
								itemList.add(item);
							}
						}
					} else {
						String value = n.text();
						Item item = new Item();
						item.setType(Item.type_text);
						item.setValue(value);
						itemList.add(item);
					}
				} else if (node instanceof org.jsoup.nodes.TextNode) {
					String value = ((org.jsoup.nodes.TextNode) node).text();
					Item item = new Item();
					item.setType(Item.type_text);
					item.setValue(value);
					itemList.add(item);
				}
			}
			section.setItems(itemList);
		}
		return section;
	}
}
