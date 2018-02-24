package com.oim.fx.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.oim.common.util.HttpUrlUtil;
import com.oim.core.common.box.FontBox;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.common.util.OnlyStringUtil;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.FaceValue;
import com.onlyxiahui.im.message.data.chat.ImageValue;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

public class ContentUtil {

	static String chatLoadImagePath;

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
							if (null != value && !"".equals(value) && !"".equals(value.replace(" ", "").replace("	", ""))) {
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
								//if (!itemList.isEmpty()) {
									Section section = new Section();
									section.setItems(itemList);
									sectionList.add(section);
									itemList = new ArrayList<Item>();
								//}
								Section s = getSection(n);
								sectionList.add(s);
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
								if (null != value && !"".equals(value) && !"".equals(value.replace(" ", "").replace("	", ""))) {
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
						itemList = new ArrayList<Item>();
					}
				} else {

					// StringBuilder style = getStyleValue();
					// for (org.jsoup.nodes.Element e : elementList) {
					// e.removeAttr("style");
					// e.attr("style", style.toString());
					// }
					// writeEditorPane.setText(htmlDocument.html());
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
								// String image = handleImagePath(src);
								Item item = new Item();
								item.setType(Item.type_image);
								item.setValue(src);
								itemList.add(item);
							}
						}
					} else {
						String value = n.text();
						if (null != value && !"".equals(value) && !"".equals(value.replace(" ", "").replace("	", ""))) {
							Item item = new Item();
							item.setType(Item.type_text);
							item.setValue(value);
							itemList.add(item);
						}
					}
				} else if (node instanceof org.jsoup.nodes.TextNode) {
					String value = ((org.jsoup.nodes.TextNode) node).text();
					if (null != value && !"".equals(value)) {
						Item item = new Item();
						item.setType(Item.type_text);
						item.setValue(value);
						itemList.add(item);
					}
				}
			}
			section.setItems(itemList);
		}
		return section;
	}

	public static String getInsertNameTag(String name, String color, String time) {
		StringBuilder nameText = new StringBuilder();
		nameText.append("<div ");
		nameText.append(getFontStyle("微软雅黑", 12, color, false, false, false));
		nameText.append(">");
		nameText.append(name);
		nameText.append(" ");
		nameText.append(time);
		nameText.append("</div>");
		return nameText.toString();
	}

	public static String getInsertContentTag(Content content) {
		StringBuilder tag = new StringBuilder();

		List<Section> sections = content.getSections();
		if (null != sections) {
			com.onlyxiahui.im.message.data.chat.Font font = content.getFont();
			StringBuilder sb = new StringBuilder();
			for (Section section : sections) {
				// String padding="padding:5px;";
				StringBuilder style = new StringBuilder();
				style.append("style=\"");
				style.append(getFontStyleValue(font.getName(), font.getSize(), font.getColor(), font.isBold(), font.isUnderline(), font.isItalic()));
				// style.append(padding);
				style.append("\"");

				sb.append("<div ");
				sb.append("style=\"");
				sb.append("height: auto;overflow: hidden;padding-left:15px;padding-right: 12px;");
				sb.append("\"");
				sb.append(">");

				sb.append("	<div ");
				sb.append(style);
				sb.append(">");

				List<Item> items = section.getItems();
				if (null != items) {
					for (Item item : items) {
						if (Item.type_text.equals(item.getType())) {
							String text = item.getValue();

							text = OnlyStringUtil.html(text);
							String path = "Resources/Images/Face/emoji/23x23/";
							text = FaceUtil.toEmojiImage(text, path, ".png");
							String a = "onclick=\"oim.openUrl('$1');\"";
							text = HttpUrlUtil.replaceUrlToDisableLink(text, a);
							sb.append(text);
						}
						if (Item.type_face.equals(item.getType())) {
							String faceInfo = item.getValue();
							if (null != faceInfo && !"".equals(faceInfo)) {
								if (OnlyJsonUtil.mayBeJSON(faceInfo)) {
									try {
										FaceValue iv = OnlyJsonUtil.jsonToObject(faceInfo, FaceValue.class);
										String categoryId = iv.getCategoryId();
										String value = iv.getKey();
										String fullPath = FaceUtil.getFacePath(categoryId, value);
										sb.append(getImageTag("", "face", faceInfo, fullPath));
									} catch (Exception e) {
									}
								}else{
									String[] array = faceInfo.split(",");
									if (array.length > 1) {
										String categoryId = array[0];
										String value = array[1];
										String fullPath = FaceUtil.getFacePath(categoryId, value);
										sb.append(getImageTag("", "face", faceInfo, fullPath));
									}
								}
							}
						}
						if (Item.type_image.equals(item.getType())) {
							String imageInfo = item.getValue();
							if (null != imageInfo && !"".equals(imageInfo)) {
								if (OnlyJsonUtil.mayBeJSON(imageInfo)) {
									try {
										ImageValue iv = OnlyJsonUtil.jsonToObject(imageInfo, ImageValue.class);

										String id = iv.getId();
										String tempImage = getChatLoadImagePath();
										sb.append(getImageTag(id, "image", item.getValue(), tempImage));
									} catch (Exception e) {
									}
								}
							}
						}
					}
				}
				sb.append("	</div>");
				sb.append("</div>");
			}
			tag.append(sb);
		}
		return tag.toString();
	}

	
	public static String getBubbleInsertContentTag(Content content) {
		StringBuilder tag = new StringBuilder();

		List<Section> sections = content.getSections();
		if (null != sections) {
			com.onlyxiahui.im.message.data.chat.Font font = content.getFont();
			
			StringBuilder style = new StringBuilder();
			style.append("style=\"");
			style.append(getFontStyleValue(font.getName(), font.getSize(), font.getColor(), font.isBold(), font.isUnderline(), font.isItalic()));
			style.append("\"");
			StringBuilder sb = new StringBuilder();
			sb.append("<div ");
			sb.append(style);
			sb.append(">");
			
			
			for (Section section : sections) {
				sb.append("<div>");
				
				

				List<Item> items = section.getItems();
				if (null != items) {
					for (Item item : items) {
						if (Item.type_text.equals(item.getType())) {
							String text = item.getValue();

							text = OnlyStringUtil.html(text);
							String path = "Resources/Images/Face/emoji/23x23/";
							text = FaceUtil.toEmojiImage(text, path, ".png");
							String a = "onclick=\"oim.openUrl('$1');\"";
							text = HttpUrlUtil.replaceUrlToDisableLink(text, a);
							sb.append(text);
						}
						if (Item.type_face.equals(item.getType())) {
							String faceInfo = item.getValue();
							if (null != faceInfo && !"".equals(faceInfo)) {
								String[] array = faceInfo.split(",");
								if (array.length > 1) {
									String categoryId = array[0];
									String value = array[1];
									String fullPath = FaceUtil.getFacePath(categoryId, value);
									sb.append(getImageTag("", "face", faceInfo, fullPath));
								}
							}
						}
						if (Item.type_image.equals(item.getType())) {
							String imageInfo = item.getValue();
							if (null != imageInfo && !"".equals(imageInfo)) {
								if (OnlyJsonUtil.mayBeJSON(imageInfo)) {
									try {
										ImageValue iv = OnlyJsonUtil.jsonToObject(imageInfo, ImageValue.class);

										String id = iv.getId();
										String tempImage = getChatLoadImagePath();
										sb.append(getImageTag(id, "image", item.getValue(), tempImage));
									} catch (Exception e) {
									}
								}
							}
						}
					}
				}
				sb.append("	</div>");
			}
			sb.append("</div>");
			tag.append(sb);
		}
		return tag.toString();
	}
	private static StringBuilder getImageTag(String id, String name, String value, String path) {
		// <div style="max-width:395px; max-width:744px">
		// <img src="images/zs_img01.gif" style="width:100%; height:100%; " />
		// </div>

		StringBuilder image = new StringBuilder();
		// image.append("<div style=\"max-width:360px; max-height:310px\">");
		image.append("	<img ");
		if (null != id && !"".equals(id)) {
			image.append(" id=\"");
			image.append(id);
			image.append("\"");
		}
		if (null != name && !"".equals(name)) {
			image.append(" name=\"");
			image.append(name);
			image.append("\"");
		}
		if (null != value && !"".equals(value)) {
			image.append(" value=\"");
			image.append(value);
			image.append("\"");
		}
		String p = path.replace("\\", "/");
		image.append(" src=\"file:");
		if (p.startsWith("/")) {

		} else {
			image.append("/");
		}
		image.append(p);
		image.append("\" ");
		image.append("	style=\" max-width: 100%; height: auto;\" ");
		image.append(" />");

		// image.append("</div>");
		return image;
	}

	private static StringBuilder getFontStyle(String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		StringBuilder style = new StringBuilder();
		style.append("style=\"");
		style.append(getFontStyleValue(fontName, fontSize, color, bold, underline, italic));
		style.append("\"");
		return style;
	}

	/**
	 * 这里组装聊天内容的样式，字体、大小、颜色、下划线、粗体、倾斜等
	 *
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param fontName
	 * @param fontSize
	 * @param color
	 * @param bold
	 * @param underline
	 * @param italic
	 * @return
	 */
	private static StringBuilder getFontStyleValue(String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		StringBuilder style = new StringBuilder();
		style.append("font-family:").append(FontBox.getFontName(fontName)).append(";");
		style.append("font-size:").append(fontSize).append("px;");
		if (underline) {
			style.append("margin-top:0;text-decoration:underline;");
		} else {
			style.append("margin-top:0;");
		}
		if (italic) {
			style.append("font-style:italic;");
		}
		if (bold) {
			style.append("font-weight:bold;");
		}
		if (null != color) {
			style.append("color:#");
			style.append(color);
			style.append(";");
		}
		return style;
	}

	
	

	public static String getChatLoadImagePath() {
		if (null == chatLoadImagePath) {
			String tempImage = "Resources/Images/Default/ChatFrame/ImageLoading/image_loading.gif";
			File file = new File(tempImage);
			if (file.exists()) {
				chatLoadImagePath = file.getAbsolutePath();
			} else {
				chatLoadImagePath = "";
			}
		}
		return chatLoadImagePath;
	}

	// /**
	// * 提取信息中的文本信息
	// *
	// * @author XiaHui
	// * @date 2017年6月17日 下午8:02:25
	// * @param content
	// * @return
	// */
	// public static String getText(Content content) {
	// StringBuilder sb = new StringBuilder();
	// if (null != content) {
	// List<Section> sections = content.getSections();
	// if (null != sections) {
	// for (Section s : sections) {
	// List<Item> items = s.getItems();
	// if (null != items) {
	// for (Item i : items) {
	// if (Item.type_text.equals(i.getType())) {
	// sb.append(i.getValue());
	// }
	// if (Item.type_face.equals(i.getType())) {
	// sb.append("[表情]");
	// }
	// if (Item.type_image.equals(i.getType())) {
	// sb.append("[图片]");
	// }
	// if (sb.length() > 60) {
	// break;
	// }
	// }
	// }
	// }
	// }
	// }
	// return sb.toString();
	// }
}
