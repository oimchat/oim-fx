package com.oim.core.business.manager;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oim.core.business.box.FaceBox;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.oim.face.bean.FaceInfo;
import com.onlyxiahui.oim.face.util.EmojiUtil;

/**
 * @author: XiaHui
 * @date: 2017年6月19日 上午9:31:04
 */
public class FaceManager extends AbstractManager {

	public FaceManager(AppContext appContext) {
		super(appContext);
		initData();
	}

	private void initData() {

	}

	public String getFaceImageTag(FaceInfo fi) {
		String categoryId = fi.getCategoryId();
		String key = fi.getKey();
		String value = categoryId + "," + key;
		String source = fi.getRealPath();
		String alt = fi.getText();
		String tag = getImageTag("", "face", value, source, alt);
		return tag;
	}

	public String getFaceImageTag(String categoryId, String key) {
		String value = categoryId + "," + key;
		String source;
		String alt = "";
		FaceBox box = appContext.getBox(FaceBox.class);
		String tag = null;
		if ("emoji".equals(categoryId)) {
			String image = EmojiUtil.emojiImage(key);

			URL url = box.getEmojiURL(image);
			if (null != url) {
				String path = url.toString();
				tag = getImageTag("", "face", "emoji," + key, path, "");
			}
		} else {
			FaceInfo fi = box.getFaceInfo(categoryId, key);
			if (null != fi) {
				source = fi.getRealPath();
				alt = fi.getText();
				tag = getImageTag("", "face", value, source, alt);
			} else {
				source = box.getFacePath(categoryId, key);
				if (null != source) {
					tag = getImageTag("", "face", value, source, alt);
				}
			}
		}
		if (tag == null || "".equals(tag)) {
			tag = "<label>[不支持的表情]</label>";
		}
		return tag;
	}

	public String toEmojiImage(String source) {
		if (source != null) {
			FaceBox box = appContext.getBox(FaceBox.class);
			Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			while (emojiMatcher.find()) {
				String e = emojiMatcher.group();
				String code = EmojiUtil.emojiCode(e);
				String image = EmojiUtil.emojiImage(code);

				URL url = box.getEmojiURL(image);
				if (null != url) {
					String path = url.toString();
					String imageTag = getImageTag("", "face", "emoji," + code, path, "");
					source = source.replace(e, imageTag);
				}
				// source = emojiMatcher.replaceAll("*");
			}
		}
		return source;
	}

	private static String getImageTag(String id, String name, String value, String source, String alt) {
		StringBuilder image = new StringBuilder();
		image.append("<img ");
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
		if (null != source && !"".equals(source)) {
			image.append(" src=\"");
			image.append(source);
			image.append("\" ");
		}

		if (null != alt && !"".equals(alt)) {
			image.append(" alt=\"");
			image.append(alt);
			image.append("\"");
		}
		image.append(" />");
		return image.toString();
	}
}
