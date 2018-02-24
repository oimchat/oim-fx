package com.oim.core.business.service;

import java.io.File;
import java.util.List;

import com.oim.common.chat.util.HtmlContentUtil;
import com.oim.common.chat.util.WebImagePathUtil;
import com.oim.common.util.HttpUrlUtil;
import com.oim.core.business.manager.FaceManager;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.common.util.OnlyStringUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.FaceValue;
import com.onlyxiahui.im.message.data.chat.ImageValue;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

/**
 * @author XiaHui
 * @date 2017-11-10 11:02:54
 */
public class ContentToHtmlService extends AbstractService {

	static String chatLoadImagePath;

	public ContentToHtmlService(AppContext appContext) {
		super(appContext);
	}

	public String getInsertContentTag(Content content) {

		StringBuilder tag = new StringBuilder();

		List<Section> sections = content.getSections();
		if (null != sections) {
			com.onlyxiahui.im.message.data.chat.Font font = content.getFont();

			StringBuilder tagStyle = new StringBuilder();
			tagStyle.append("height: auto;overflow: hidden;padding-left:15px;padding-right: 12px;");

			StringBuilder style = new StringBuilder();
			style.append("style=\"");
			style.append(tagStyle);
			style.append(HtmlContentUtil.getFontStyleValue(font.getName(), font.getSize(), font.getColor(), font.isBold(), font.isUnderline(), font.isItalic()));
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
						StringBuilder itemTag = createItem(item);
						sb.append(itemTag);
					}
				}
				sb.append("	</div>");
			}
			sb.append("</div>");
			tag.append(sb);
		}
		return tag.toString();
	}

	public String getBubbleInsertContentTag(Content content) {
		StringBuilder tag = new StringBuilder();

		List<Section> sections = content.getSections();
		if (null != sections) {
			com.onlyxiahui.im.message.data.chat.Font font = content.getFont();

			StringBuilder style = new StringBuilder();
			style.append("style=\"");
			style.append(HtmlContentUtil.getFontStyleValue(font.getName(), font.getSize(), font.getColor(), font.isBold(), font.isUnderline(), font.isItalic()));
			style.append("\"");

			StringBuilder sb = new StringBuilder();
			sb.append("<div ");
			sb.append(style);
			sb.append(">");
			for (Section section : sections) {

				List<Item> items = section.getItems();
				boolean hasNode = (null != items) && !items.isEmpty();
				if (hasNode) {
					sb.append("<div>");
					for (Item item : items) {
						StringBuilder itemTag = createItem(item);
						sb.append(itemTag);
					}
					sb.append("	</div>");
				} else {
					sb.append("<br>");
				}
			}
			sb.append("</div>");
			tag.append(sb);
		}
		return tag.toString();
	}

	public StringBuilder createItem(Item item) {
		StringBuilder sb = new StringBuilder();
		if (Item.type_text.equals(item.getType())) {
			String text = item.getValue();

			FaceManager fm = appContext.getManager(FaceManager.class);
			text = OnlyStringUtil.html(text);
			fm.toEmojiImage(text);
			String a = "onclick=\"oim.openUrl('$1');\"";
			text = HttpUrlUtil.replaceUrlToDisableLink(text, a);
			sb.append(text);
		}
		if (Item.type_face.equals(item.getType())) {
			String faceInfo = item.getValue();
			if (null != faceInfo && !"".equals(faceInfo)) {
				FaceManager fm = appContext.getManager(FaceManager.class);
				if (OnlyJsonUtil.mayBeJSON(faceInfo)) {
					try {
						FaceValue iv = OnlyJsonUtil.jsonToObject(faceInfo, FaceValue.class);
						String categoryId = iv.getCategoryId();
						String value = iv.getKey();
						String tag = fm.getFaceImageTag(categoryId, value);
						sb.append(tag);
					} catch (Exception e) {
					}
				} else {
					String[] array = faceInfo.split(",");
					if (array.length > 1) {
						String categoryId = array[0];
						String value = array[1];
						String tag = fm.getFaceImageTag(categoryId, value);
						sb.append(tag);
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
						String url = iv.getUrl();
						String tempImage = getChatLoadImagePath();
						tempImage = WebImagePathUtil.pathToFileImageSource(tempImage);
						sb.append(HtmlContentUtil.getImageTag(id, "image", url, tempImage));
					} catch (Exception e) {

					}
				}
			}
		}
		return sb;
	}

	public static String getChatLoadImagePath() {
		if (null == chatLoadImagePath) {
			String tempImage = "Resources/Images/Default/Chat/ImageLoading/image_loading.gif";
			File file = new File(tempImage);
			if (file.exists()) {
				chatLoadImagePath = file.getAbsolutePath();
			} else {
				chatLoadImagePath = "";
			}
		}
		return chatLoadImagePath;
	}
}
