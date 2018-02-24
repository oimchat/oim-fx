package com.oim.fx.ui.chat;


import com.sun.webkit.Pasteboard;
import com.sun.webkit.graphics.WCGraphicsManager;
import com.sun.webkit.graphics.WCImageFrame;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
/**
 * @author: XiaHui
 * @date: 2017年7月31日 下午1:55:22
 */
final class PasteboardImpl implements Pasteboard {

	private final Clipboard clipboard = Clipboard.getSystemClipboard();

	PasteboardImpl() {

	}

	@Override
	public String getPlainText() {
		String text = clipboard.getString();
		return text;
	}

	@Override
	public String getHtml() {
		String html = clipboard.getHtml();
		if (null != html) {
			html = html.replaceAll("<(?!(img|IMG))[^>]*>", "");
//			String path = "Resources/Images/Face/emoji/23x23/";
//			html = FaceUtil.toEmojiImage(html, path, ".png");
		}
		return html;
	}

	@Override
	public void writePlainText(String text) {
		ClipboardContent content = new ClipboardContent();
		content.putString(text);
		clipboard.setContent(content);
	}

	@Override
	public void writeSelection(boolean canSmartCopyOrDelete, String text, String html) {
		ClipboardContent content = new ClipboardContent();
		content.putString(text);
		content.putHtml(html);
		clipboard.setContent(content);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void writeImage(WCImageFrame wcImage) {
		Object platformImage = WCGraphicsManager.getGraphicsManager().toPlatformImage(wcImage.getFrame());
		Image fxImage = Image.impl_fromPlatformImage(platformImage);
		ClipboardContent content = new ClipboardContent();
		content.putImage(fxImage);
		clipboard.setContent(content);
	}

	@Override
	public void writeUrl(String url, String markup) {
		ClipboardContent content = new ClipboardContent();
		content.putString(url);
		content.putHtml(markup);
		content.putUrl(url);
		clipboard.setContent(content);
	}
}
